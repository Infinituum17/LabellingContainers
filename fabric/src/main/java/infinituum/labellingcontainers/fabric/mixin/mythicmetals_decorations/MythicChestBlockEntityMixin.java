package infinituum.labellingcontainers.fabric.mixin.mythicmetals_decorations;

import infinituum.labellingcontainers.utils.ChestHelper;
import infinituum.labellingcontainers.utils.TaggableChest;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import nourl.mythicmetalsdecorations.blocks.chest.MythicChestBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MythicChestBlockEntity.class)
public class MythicChestBlockEntityMixin extends BlockEntity implements TaggableChest {
    @Unique
    private MutableComponent labellingcontainers$label = Component.literal("");
    @Unique
    private Item labellingcontainers$displayItem = Items.AIR;

    public MythicChestBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return super.saveWithoutMetadata();
    }

    @Unique
    private void labellingcontainers$notifyClients(BlockState oldState) {
        super.setChanged();
        if (level != null)
            level.sendBlockUpdated(this.worldPosition, oldState, this.getBlockState(), Block.UPDATE_CLIENTS);
    }

    @Override
    public Item labellingcontainers$getDisplayItem() {
        return labellingcontainers$displayItem;
    }

    @Override
    public void labellingcontainers$setDisplayItem(Item item) {
        labellingcontainers$setDisplayItem(item, true);
    }

    @Override
    public void labellingcontainers$setDisplayItem(Item item, boolean searchDoubleChest) {
        BlockState oldState = this.getBlockState();

        labellingcontainers$displayItem = item;
        if (searchDoubleChest) {
            TaggableChest otherChest = (TaggableChest) ChestHelper.getConnectedChestBlockEntity(level, worldPosition, this.getBlockState());

            if (otherChest != null) {
                otherChest.labellingcontainers$setDisplayItem(item, false);
            }
        }

        labellingcontainers$notifyClients(oldState);
    }

    @Override
    public MutableComponent labellingcontainers$getLabel() {
        return labellingcontainers$label;
    }

    @Override
    public void labellingcontainers$setLabel(MutableComponent newLabel) {
        labellingcontainers$setLabel(newLabel, true);
    }

    @Override
    public void labellingcontainers$setLabel(MutableComponent newLabel, boolean searchDoubleChest) {
        BlockState oldState = this.getBlockState();

        labellingcontainers$label = newLabel;
        if (searchDoubleChest) {
            TaggableChest otherChest = (TaggableChest) ChestHelper.getConnectedChestBlockEntity(level, worldPosition, this.getBlockState());

            if (otherChest != null) {
                otherChest.labellingcontainers$setLabel(newLabel, false);
            }
        }

        labellingcontainers$notifyClients(oldState);
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    public void writeNbtMixin(CompoundTag nbt, CallbackInfo ci) {
        nbt.putString("label", labellingcontainers$label.getString());
        CompoundTag displayItemNbt = new CompoundTag();

        new ItemStack(labellingcontainers$displayItem).save(displayItemNbt);

        if (labellingcontainers$displayItem != null) {
            nbt.put("displayItem", displayItemNbt);
        }
    }

    @Inject(method = "load", at = @At("TAIL"))
    public void readNbtMixin(CompoundTag nbt, CallbackInfo ci) {
        this.labellingcontainers$label = Component.nullToEmpty(nbt.getString("label")).copy();
        if (nbt.contains("displayItem")) {
            this.labellingcontainers$displayItem = ItemStack.of(nbt.getCompound("displayItem")).getItem();
        }
    }
}