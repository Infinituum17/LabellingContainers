package infinituum.labellingcontainers.items;

import dev.architectury.registry.menu.MenuRegistry;
import infinituum.labellingcontainers.config.TaggableBlocks;
import infinituum.labellingcontainers.registration.ItemRegistration;
import infinituum.labellingcontainers.screens.LabelPrinterScreenFactory;
import infinituum.labellingcontainers.utils.ActionBarTextHelper;
import infinituum.labellingcontainers.utils.BlockEntityHelper;
import infinituum.labellingcontainers.utils.InventoryHelper;
import infinituum.labellingcontainers.utils.Taggable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static infinituum.labellingcontainers.LabellingContainersConfig.TAGGABLE_BLOCKS_CONFIG;
import static net.minecraft.world.item.Items.AIR;

public class LabelPrinterItem extends Item {

    public LabelPrinterItem(Properties settings) {
        super(settings);
    }

    public static String getLabel(ItemStack itemStack) {
        CompoundTag labelTag = itemStack.getOrCreateTagElement("Label");

        if (!labelTag.contains("text")) {
            labelTag.putString("text", "");
        }

        return labelTag.getString("text");
    }

    public static void setLabel(ItemStack itemStack, String text) {
        CompoundTag tag = itemStack.getOrCreateTagElement("Label");

        tag.putString("text", text);

        itemStack.addTagElement("Label", tag);
    }

    public static Item getDisplayItem(ItemStack itemStack) {
        CompoundTag labelTag = itemStack.getOrCreateTagElement("Label");

        if (!labelTag.contains("displayItem")) {
            labelTag.put("displayItem", ItemStack.EMPTY.save(new CompoundTag()));
        }

        return ItemStack.of(labelTag.getCompound("displayItem")).getItem();
    }

    public static void setDisplayItem(ItemStack itemStack, ItemStack itemToDisplay) {
        CompoundTag tag = itemStack.getOrCreateTagElement("Label");

        tag.put("displayItem", itemToDisplay.save(new CompoundTag()));

        itemStack.addTagElement("Label", tag);
    }

    public static int getModeIndex(ItemStack itemStack) {
        CompoundTag labelTag = itemStack.getOrCreateTagElement("Label");

        if (!labelTag.contains("modeIndex")) {
            labelTag.putInt("modeIndex", 0);
        }

        return labelTag.getInt("modeIndex");
    }

    public static LabelPrinterMode getMode(ItemStack itemStack) {
        int i = getModeIndex(itemStack);

        return LabelPrinterMode.fromIndex(i);
    }

    public static void setModeIndex(ItemStack itemStack, int modeIndex) {
        CompoundTag tag = itemStack.getOrCreateTagElement("Label");

        tag.putInt("modeIndex", modeIndex);

        itemStack.addTagElement("Label", tag);
    }

    public static void setMode(ItemStack itemStack, LabelPrinterMode mode) {
        int i = mode.ordinal();

        setModeIndex(itemStack, i);
    }

    private InteractionResult interactionFail(Level level, Vec3 hitPos, BlockPos pos) {
        ((ServerLevel) level).sendParticles(ParticleTypes.SMOKE, hitPos.x(), hitPos.y(), hitPos.z(), 15, 0, 0, 0, 0.01);
        level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.75f, 2f);

        return InteractionResult.FAIL;
    }

    private InteractionResult interactionFail(Level level, Vec3 hitPos, BlockPos pos, Player player, String errorTranslationKey) {
        ActionBarTextHelper.sendMessage(
                (ServerPlayer) player,
                Component
                        .translatable(ItemRegistration.LABEL_PRINTER.get().getDescriptionId() + errorTranslationKey)
                        .withStyle(ChatFormatting.RED)
        );

        return interactionFail(level, hitPos, pos);
    }

    private InteractionResult interactionSuccess(Level level, Vec3 hitPos, BlockPos pos) {
        ((ServerLevel) level).sendParticles(ParticleTypes.END_ROD, hitPos.x(), hitPos.y(), hitPos.z(), 15, 0, 0, 0, 0.01);
        level.playSound(null, pos, SoundEvents.NOTE_BLOCK_HARP.value(), SoundSource.BLOCKS, 0.75f, 2f);

        return InteractionResult.SUCCESS;
    }

    private InteractionResult interactionSuccess(Level level, Vec3 hitPos, BlockPos pos, Player player, String successTranslationKey) {
        ActionBarTextHelper.sendMessage(
                (ServerPlayer) player,
                Component
                        .translatable(ItemRegistration.LABEL_PRINTER.get().getDescriptionId() + successTranslationKey)
                        .withStyle(ChatFormatting.GOLD)
        );

        return interactionSuccess(level, hitPos, pos);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();

        if (player == null) return super.useOn(context);
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        Inventory inventory = player.getInventory();
        BlockPos clickedBlockPosition = context.getClickedPos();
        ItemStack itemInHand = context.getItemInHand();
        BlockState blockState = level.getBlockState(clickedBlockPosition);
        ResourceLocation registryName = blockState.getBlock().asItem().arch$registryName();
        Vec3 hitPosVec3 = context.getClickLocation();

        if (registryName == null) return super.useOn(context);

        BlockEntity blockEntity = BlockEntityHelper.locateTargetBlockEntity(level, clickedBlockPosition, blockState);

        if (!(blockEntity instanceof Taggable taggable)) {
            if (player.isCrouching()) {
                swapMode(itemInHand);
            } else {
                MenuRegistry.openMenu((ServerPlayer) player, new LabelPrinterScreenFactory());
            }

            return InteractionResult.SUCCESS;
        }

        MutableComponent printerLabel = Component.literal(getLabel(itemInHand));
        Item printerDisplayItem = getDisplayItem(itemInHand);

        Component blockLabel = taggable.labellingcontainers$getLabel();
        Item blockDisplayItem = taggable.labellingcontainers$getDisplayItem();

        switch (getMode(itemInHand)) {
            case CREATE -> {
                TaggableBlocks config = TAGGABLE_BLOCKS_CONFIG.getConfig();

                if (config.isLimited() && !(config.has(registryName.toString()) || config.hasAnyTag(blockState.getTags()))) {
                    return interactionFail(level, hitPosVec3, clickedBlockPosition, player, ".untaggable.error");
                }

                if (!inventory.contains(Items.PAPER.getDefaultInstance()) && !player.isCreative()) {
                    return interactionFail(level, hitPosVec3, clickedBlockPosition, player, ".paper.error");
                }

                if (blockLabel.equals(printerLabel) && blockDisplayItem.equals(printerDisplayItem)) {
                    return interactionFail(level, hitPosVec3, clickedBlockPosition);
                }

                if (!player.isCreative()) InventoryHelper.removeOneItemFromInventory(inventory, Items.PAPER);

                taggable.labellingcontainers$setLabel(printerLabel, true);
                taggable.labellingcontainers$setDisplayItem(printerDisplayItem, true);

                return interactionSuccess(level, hitPosVec3, clickedBlockPosition);
            }
            case COPY -> {
                if (blockLabel.equals(printerLabel) && blockDisplayItem.equals(printerDisplayItem)) {
                    return interactionFail(level, hitPosVec3, clickedBlockPosition);
                }

                setLabel(itemInHand, blockLabel.getString());
                setDisplayItem(itemInHand, blockDisplayItem.getDefaultInstance());

                return interactionSuccess(level, hitPosVec3, clickedBlockPosition, player, ".mode.copy.success");
            }
        }

        return super.useOn(context);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) return super.use(level, player, hand);

        if (hand == InteractionHand.MAIN_HAND) {
            if (player.isCrouching()) {
                swapMode(player.getItemInHand(hand));
            } else {
                MenuRegistry.openMenu((ServerPlayer) player, new LabelPrinterScreenFactory());
            }
        }

        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag context) {
        String currentLabel = getLabel(stack);
        Item currentDisplayItem = getDisplayItem(stack);
        int currentModeIndex = getModeIndex(stack);

        MutableComponent descriptionText = Component.literal("ⓘ ").withStyle(ChatFormatting.BLUE);

        if (level != null && level.isClientSide() && Screen.hasShiftDown()) {
            descriptionText.append(Component.translatable(this.getDescriptionId() + ".tooltip.description").withStyle(ChatFormatting.GREEN));
        } else {
            descriptionText.append(Component.translatable(this.getDescriptionId() + ".tooltip.hidden").withStyle(ChatFormatting.GRAY));
        }

        tooltip.add(descriptionText);
        tooltip.add(Component.literal(""));

        MutableComponent labelText = Component.literal("● ").withStyle(ChatFormatting.GRAY);
        labelText.append(Component.translatable(this.getDescriptionId() + ".tooltip.label").withStyle(ChatFormatting.GRAY));

        if (currentLabel.isEmpty()) {
            labelText.append(Component.translatable(this.getDescriptionId() + ".tooltip.none").withStyle(ChatFormatting.DARK_RED));
        } else {
            labelText.append(Component.literal("\"" + currentLabel + "\"").withStyle(ChatFormatting.GOLD));
        }

        tooltip.add(labelText);

        MutableComponent displayItemText = Component.literal("● ").withStyle(ChatFormatting.GRAY);
        displayItemText.append(Component.translatable(this.getDescriptionId() + ".tooltip.display_item").withStyle(ChatFormatting.GRAY));

        if (currentDisplayItem.equals(AIR)) {
            displayItemText.append(Component.translatable(this.getDescriptionId() + ".tooltip.none").withStyle(ChatFormatting.DARK_RED));
        } else {
            displayItemText.append(currentDisplayItem.getDescription().copy().withStyle(ChatFormatting.AQUA));
        }

        tooltip.add(displayItemText);

        MutableComponent modeText = Component.literal("● ").withStyle(ChatFormatting.GRAY);
        modeText.append(Component.translatable(this.getDescriptionId() + ".tooltip.mode").withStyle(ChatFormatting.GRAY));

        modeText.append(LabelPrinterMode.fromIndex(currentModeIndex).getDisplayable());

        tooltip.add(modeText);

        super.appendHoverText(stack, level, tooltip, context);
    }

    private void swapMode(ItemStack currentItemStack) {
        LabelPrinterMode mode = getMode(currentItemStack);

        setMode(currentItemStack, mode.swap());
    }

    public enum LabelPrinterMode {
        CREATE(Component.translatable("item.labellingcontainers.label_printer.mode.create").withStyle(ChatFormatting.RED)),
        COPY(Component.translatable("item.labellingcontainers.label_printer.mode.copy").withStyle(ChatFormatting.GOLD));

        private final Component name;

        LabelPrinterMode(Component s) {
            this.name = s;
        }

        public static LabelPrinterMode fromIndex(int index) {
            return LabelPrinterMode.values()[index];
        }

        public Component getDisplayable() {
            return name;
        }

        public LabelPrinterMode swap() {
            if (this == LabelPrinterMode.CREATE) {
                return LabelPrinterMode.COPY;
            } else {
                return LabelPrinterMode.CREATE;
            }
        }
    }
}