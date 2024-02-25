package infinituum.labellingcontainers.items;

import dev.architectury.registry.menu.MenuRegistry;
import infinituum.labellingcontainers.PlatformHelper;
import infinituum.labellingcontainers.registration.ItemRegistration;
import infinituum.labellingcontainers.screens.LabelPrinterScreenFactory;
import infinituum.labellingcontainers.utils.InventoryHelper;
import infinituum.labellingcontainers.utils.Taggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.item.Items.AIR;

public class LabelPrinterItem extends Item {
    public LabelPrinterItem(Settings settings) {
        super(settings);
    }

    private String getLabel(ItemStack itemStack) {
        NbtCompound labelNbt = itemStack.getSubNbt("Label");
        return (labelNbt != null) ? labelNbt.getString("text") : "";
    }

    private Item getDisplayItem(ItemStack itemStack) {
        NbtCompound displayItemNbt = itemStack.getSubNbt("Contents");
        return (displayItemNbt != null) ? ItemStack.fromNbt(displayItemNbt).getItem() : ItemStack.EMPTY.getItem();
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();

        if (player == null) return super.useOnBlock(context);

        PlayerInventory inventory = player.getInventory();
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        ItemStack itemStack = context.getStack();
        BlockEntity blockEntity = PlatformHelper.locateTargetBlockEntity(world, pos);

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
                world.playSound(null, pos, SoundEvents.BLOCK_NOTE_BLOCK_HARP.value(), SoundCategory.BLOCKS, 0.75f, 2f);
            }

            return ActionResult.SUCCESS;
        }

        return super.useOnBlock(context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            MenuRegistry.openMenu((ServerPlayerEntity) user, new LabelPrinterScreenFactory());
        }

        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        String currentLabel = getLabel(stack);
        Item currentDisplayItem = getDisplayItem(stack);

        MutableText descriptionText = Text.literal("ⓘ ").formatted(Formatting.BLUE);

        if (world != null && world.isClient() && Screen.hasShiftDown()) {
            descriptionText.append(Text.translatable(this.getTranslationKey() + ".tooltip.description").formatted(Formatting.GREEN));
        } else {
            descriptionText.append(Text.translatable(this.getTranslationKey() + ".tooltip.hidden").formatted(Formatting.GRAY));
        }

        tooltip.add(descriptionText);

        MutableText labelText = Text.literal("● ").formatted(Formatting.GRAY);
        labelText.append(Text.translatable(this.getTranslationKey() + ".tooltip.label").formatted(Formatting.GRAY));

        if (currentLabel.isEmpty()) {
            labelText.append(Text.translatable(this.getTranslationKey() + ".tooltip.none").formatted(Formatting.DARK_RED));
        } else {
            labelText.append(Text.literal("\"" + currentLabel + "\"").formatted(Formatting.GOLD));
        }

        tooltip.add(Text.literal(""));
        tooltip.add(labelText);

        MutableText displayItemText = Text.literal("● ").formatted(Formatting.GRAY);
        displayItemText.append(Text.translatable(this.getTranslationKey() + ".tooltip.display_item").formatted(Formatting.GRAY));

        if (currentDisplayItem.equals(AIR)) {
            displayItemText.append(Text.translatable(this.getTranslationKey() + ".tooltip.none").formatted(Formatting.DARK_RED));
        } else {
            displayItemText.append(currentDisplayItem.getName().copy().formatted(Formatting.AQUA));
        }

        tooltip.add(displayItemText);

        super.appendTooltip(stack, world, tooltip, context);
    }
}