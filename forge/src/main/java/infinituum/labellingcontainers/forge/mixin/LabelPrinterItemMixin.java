package infinituum.labellingcontainers.forge.mixin;

import infinituum.labellingcontainers.items.LabelPrinterItem;
import infinituum.labellingcontainers.registration.ItemRegistration;
import infinituum.labellingcontainers.utils.InventoryHelper;
import infinituum.labellingcontainers.utils.Taggable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.cyclops.colossalchests.block.ChestWall;
import org.cyclops.colossalchests.block.ColossalChest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LabelPrinterItem.class)
public class LabelPrinterItemMixin extends Item {
    public LabelPrinterItemMixin(Settings settings) {
        super(settings);
    }

    @Shadow
    private String getLabel(ItemStack itemStack) {
        NbtCompound labelNbt = itemStack.getSubNbt("Label");
        return (labelNbt != null) ? labelNbt.getString("text") : "";
    }

    @Shadow
    private Item getDisplayItem(ItemStack itemStack) {
        NbtCompound displayItemNbt = itemStack.getSubNbt("Contents");
        return (displayItemNbt != null) ? ItemStack.fromNbt(displayItemNbt).getItem() : ItemStack.EMPTY.getItem();
    }

    /**
     * @author Infinituum17
     * @reason Advanced interactions with Forge-only mods
     */
    @Overwrite
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();

        if (player == null) return super.useOnBlock(context);

        PlayerInventory inventory = player.getInventory();
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        ItemStack itemStack = context.getStack();
        BlockState blockState = world.getBlockState(pos);
        BlockEntity blockEntity = null;

        if(blockState.getBlock() instanceof ChestWall && blockState.get(ColossalChest.ENABLED)) {
            BlockPos corePos = ColossalChest.getCoreLocation(((ChestWall) blockState.getBlock()).getMaterial(), world, pos);
            if (corePos != null) blockEntity = world.getBlockEntity(corePos);
        } else {
            blockEntity = world.getBlockEntity(pos);
        }

        if (blockEntity instanceof Taggable taggable) {
            if (!world.isClient()) {
                MutableText label = Text.literal(getLabel(itemStack));
                Item displayItem = getDisplayItem(itemStack);
                Vec3d hitPos = context.getHitPos();

                if (!inventory.contains(Items.PAPER.getDefaultStack()) && !player.isCreative()) {
                    Text message = Text
                            .translatable(ItemRegistration.LABEL_PRINTER.get().getTranslationKey() + ".paper.error")
                            .formatted(Formatting.RED);
                    player.sendMessage(message);

                    ((ServerWorld) world).spawnParticles(ParticleTypes.SMOKE, hitPos.getX(), hitPos.getY(), hitPos.getZ(), 15, 0, 0, 0, 0.01);
                    world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.75f, 2f);

                    return ActionResult.FAIL;
                }

                if (taggable.labellingcontainers$getLabel().equals(label) && taggable.labellingcontainers$getDisplayItem().equals(displayItem)) {
                    ((ServerWorld) world).spawnParticles(ParticleTypes.SMOKE, hitPos.getX(), hitPos.getY(), hitPos.getZ(), 15, 0, 0, 0, 0.01);
                    world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.75f, 2f);
                    return ActionResult.FAIL;
                }

                if (!player.isCreative()) InventoryHelper.removeOneItemFromInventory(inventory, Items.PAPER);

                taggable.labellingcontainers$setLabel(label);
                taggable.labellingcontainers$setDisplayItem(displayItem);

                ((ServerWorld) world).spawnParticles(ParticleTypes.END_ROD, hitPos.getX(), hitPos.getY(), hitPos.getZ(), 15, 0, 0, 0, 0.01);
                world.playSound(null, pos, SoundEvents.BLOCK_NOTE_BLOCK_HARP, SoundCategory.BLOCKS, 0.75f, 2f);
            }

            return ActionResult.SUCCESS;
        }

        return super.useOnBlock(context);
    }
}
