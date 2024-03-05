package infinituum.labellingcontainers.fabric.mixin.compact_storage;

import com.tabithastrong.compactstorage.block.entity.CompactBarrelBlockEntity;
import infinituum.labellingcontainers.utils.Taggable;
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
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CompactBarrelBlockEntity.class)
public class CompactBarrelBlockEntityMixin extends BlockEntity implements Taggable {
    @Unique
    private MutableComponent labellingcontainers$label = Component.literal("");
    @Unique
    private Item labellingcontainers$displayItem = Items.AIR;

    public CompactBarrelBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(((CompactBarrelBlockEntity) (Object) this));
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Unique
    private void notifyClients(BlockState oldState) {
        this.setChanged();
        if (level != null)
            level.sendBlockUpdated(this.worldPosition, oldState, this.getBlockState(), Block.UPDATE_CLIENTS);
    }

    @Override
    public Item labellingcontainers$getDisplayItem() {
        return labellingcontainers$displayItem;
    }

    @Override
    public void labellingcontainers$setDisplayItem(Item item) {
        BlockState oldState = this.getBlockState();

        labellingcontainers$displayItem = item;

        notifyClients(oldState);
    }

    @Override
    public MutableComponent labellingcontainers$getLabel() {
        return labellingcontainers$label;
    }

    @Override
    public void labellingcontainers$setLabel(MutableComponent newLabel) {
        BlockState oldState = this.getBlockState();

        labellingcontainers$label = newLabel;

        notifyClients(oldState);
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