package infinituum.labellingcontainers.items;

import dev.architectury.registry.menu.MenuRegistry;
import infinituum.labellingcontainers.PlatformHelper;
import infinituum.labellingcontainers.registration.ItemRegistration;
import infinituum.labellingcontainers.screens.LabelPrinterScreenFactory;
import infinituum.labellingcontainers.utils.InventoryHelper;
import infinituum.labellingcontainers.utils.Taggable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

import static net.minecraft.world.item.Items.AIR;

public class LabelPrinterItem extends Item {
    public LabelPrinterItem(Properties settings) {
        super(settings);
    }

    private String getLabel(ItemStack itemStack) {
        CompoundTag labelNbt = itemStack.getTagElement("Label");
        return (labelNbt != null) ? labelNbt.getString("text") : "";
    }

    private Item getDisplayItem(ItemStack itemStack) {
        CompoundTag displayItemNbt = itemStack.getTagElement("Contents");
        return (displayItemNbt != null) ? ItemStack.of(displayItemNbt).getItem() : ItemStack.EMPTY.getItem();
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();

        if (player == null) return super.useOn(context);

        Inventory inventory = player.getInventory();
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();
        ItemStack itemStack = context.getItemInHand();
        BlockEntity blockEntity = PlatformHelper.locateTargetBlockEntity(world, pos);

        if (blockEntity instanceof Taggable taggable) {
            if (!world.isClientSide()) {
                MutableComponent label = Component.literal(getLabel(itemStack));
                Item displayItem = getDisplayItem(itemStack);
                Vec3 hitPos = context.getClickLocation();

                if (!inventory.contains(Items.PAPER.getDefaultInstance()) && !player.isCreative()) {
                    Component message = Component
                            .translatable(ItemRegistration.LABEL_PRINTER.get().getDescriptionId() + ".paper.error")
                            .withStyle(ChatFormatting.RED);
                    player.sendSystemMessage(message);

                    ((ServerLevel) world).sendParticles(ParticleTypes.SMOKE, hitPos.x(), hitPos.y(), hitPos.z(), 15, 0, 0, 0, 0.01);
                    world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.75f, 2f);

                    return InteractionResult.FAIL;
                }

                if (taggable.labellingcontainers$getLabel().equals(label) && taggable.labellingcontainers$getDisplayItem().equals(displayItem)) {
                    ((ServerLevel) world).sendParticles(ParticleTypes.SMOKE, hitPos.x(), hitPos.y(), hitPos.z(), 15, 0, 0, 0, 0.01);
                    world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.75f, 2f);
                    return InteractionResult.FAIL;
                }

                if (!player.isCreative()) InventoryHelper.removeOneItemFromInventory(inventory, Items.PAPER);

                taggable.labellingcontainers$setLabel(label);
                taggable.labellingcontainers$setDisplayItem(displayItem);

                ((ServerLevel) world).sendParticles(ParticleTypes.END_ROD, hitPos.x(), hitPos.y(), hitPos.z(), 15, 0, 0, 0, 0.01);
                world.playSound(null, pos, SoundEvents.NOTE_BLOCK_HARP, SoundSource.BLOCKS, 0.75f, 2f);
            }

            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if (!world.isClientSide()) {
            MenuRegistry.openMenu((ServerPlayer) user, new LabelPrinterScreenFactory());
        }

        return super.use(world, user, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        String currentLabel = getLabel(stack);
        Item currentDisplayItem = getDisplayItem(stack);

        MutableComponent descriptionText = Component.literal("ⓘ ").withStyle(ChatFormatting.BLUE);

        if (world != null && world.isClientSide() && Screen.hasShiftDown()) {
            descriptionText.append(Component.translatable(this.getDescriptionId() + ".tooltip.description").withStyle(ChatFormatting.GREEN));
        } else {
            descriptionText.append(Component.translatable(this.getDescriptionId() + ".tooltip.hidden").withStyle(ChatFormatting.GRAY));
        }

        tooltip.add(descriptionText);

        MutableComponent labelText = Component.literal("● ").withStyle(ChatFormatting.GRAY);
        labelText.append(Component.translatable(this.getDescriptionId() + ".tooltip.label").withStyle(ChatFormatting.GRAY));

        if (currentLabel.isEmpty()) {
            labelText.append(Component.translatable(this.getDescriptionId() + ".tooltip.none").withStyle(ChatFormatting.DARK_RED));
        } else {
            labelText.append(Component.literal("\"" + currentLabel + "\"").withStyle(ChatFormatting.GOLD));
        }

        tooltip.add(Component.literal(""));
        tooltip.add(labelText);

        MutableComponent displayItemText = Component.literal("● ").withStyle(ChatFormatting.GRAY);
        displayItemText.append(Component.translatable(this.getDescriptionId() + ".tooltip.display_item").withStyle(ChatFormatting.GRAY));

        if (currentDisplayItem.equals(AIR)) {
            displayItemText.append(Component.translatable(this.getDescriptionId() + ".tooltip.none").withStyle(ChatFormatting.DARK_RED));
        } else {
            displayItemText.append(currentDisplayItem.getDescription().copy().withStyle(ChatFormatting.AQUA));
        }

        tooltip.add(displayItemText);

        super.appendHoverText(stack, world, tooltip, context);
    }
}