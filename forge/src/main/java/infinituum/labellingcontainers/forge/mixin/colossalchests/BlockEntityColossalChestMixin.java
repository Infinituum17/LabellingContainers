package infinituum.labellingcontainers.forge.mixin.colossalchests;

import infinituum.labellingcontainers.utils.Taggable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.cyclops.colossalchests.blockentity.BlockEntityColossalChest;
import org.cyclops.cyclopscore.blockentity.CyclopsBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityColossalChest.class)
public class BlockEntityColossalChestMixin extends CyclopsBlockEntity implements Taggable {

    @Unique
    private MutableComponent labellingcontainers$label = Component.literal("");
    @Unique
    private Item labellingcontainers$displayItem = Items.AIR;

    public BlockEntityColossalChestMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Unique
    private void labellingcontainers$notifyClients(BlockState oldState) {
        this.setChanged();
        if (level != null) level.sendBlockUpdated(this.worldPosition, oldState, this.getBlockState(), Block.UPDATE_CLIENTS);
    }

    @Override
    public Item labellingcontainers$getDisplayItem() {
        return labellingcontainers$displayItem;
    }

    @Override
    public void labellingcontainers$setDisplayItem(Item item) {
        BlockState oldState = this.getBlockState();

        labellingcontainers$displayItem = item;

        labellingcontainers$notifyClients(oldState);
    }

    @Override
    public MutableComponent labellingcontainers$getLabel() {
        return labellingcontainers$label;
    }

    @Override
    public void labellingcontainers$setLabel(MutableComponent newLabel) {
        BlockState oldState = this.getBlockState();

        labellingcontainers$label = newLabel;

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

    @Inject(method = "read", at = @At("TAIL"), remap = false)
    public void readNbtMixin(CompoundTag nbt, CallbackInfo ci) {
        this.labellingcontainers$label = Component.nullToEmpty(nbt.getString("label")).copy();
        if (nbt.contains("displayItem")) {
            this.labellingcontainers$displayItem = ItemStack.of(nbt.getCompound("displayItem")).getItem();
        }
    }
}
