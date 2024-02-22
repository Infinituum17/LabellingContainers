package infinituum.labellingcontainers.fabric.mixin.ironchests;

import infinituum.labellingcontainers.utils.Taggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.thatgravyboat.ironchests.common.blocks.GenericChestBlockEntity;

@Mixin(GenericChestBlockEntity.class)
public class GenericChestBlockEntityMixin extends BlockEntity implements Taggable {
    @Unique
    private MutableText label = Text.literal("");
    @Unique
    private Item displayItem = Items.AIR;

    public GenericChestBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) { super(type, pos, state); }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(((GenericChestBlockEntity)(Object)this));
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    @Unique
    private void notifyClients(BlockState oldState) {
        this.markDirty();
        if(world != null) world.updateListeners(this.pos, oldState, this.getCachedState(), Block.NOTIFY_LISTENERS);
    }

    @Override
    public Item labellingcontainers$getDisplayItem() {
        return displayItem;
    }

    @Override
    public void labellingcontainers$setDisplayItem(Item item) {
        BlockState oldState = this.getCachedState();

        displayItem = item;

        notifyClients(oldState);
    }

    @Override
    public MutableText labellingcontainers$getLabel() {
        return label;
    }

    @Override
    public void labellingcontainers$setLabel(MutableText newLabel) {
        BlockState oldState = this.getCachedState();

        label = newLabel;

        notifyClients(oldState);
    }

    @Inject(method = "writeNbt", at = @At("TAIL"))
    public void writeNbtMixin(NbtCompound nbt, CallbackInfo ci) {
        nbt.putString("label", label.getString());
        NbtCompound displayItemNbt = new NbtCompound();

        new ItemStack(displayItem).writeNbt(displayItemNbt);

        if(displayItem != null) {
            nbt.put("displayItem", displayItemNbt);
        }
    }

    @Inject(method = "readNbt", at = @At("TAIL"))
    public void readNbtMixin(NbtCompound nbt, CallbackInfo ci) {
        this.label = Text.of(nbt.getString("label")).copy();
        if(nbt.contains("displayItem")) {
            this.displayItem = ItemStack.fromNbt(nbt.getCompound("displayItem")).getItem();
        }
    }
}