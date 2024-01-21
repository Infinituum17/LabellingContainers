package infinituum.labellingcontainers.mixin;

import infinituum.labellingcontainers.utils.Taggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBoxBlockEntity.class)
public class ShulkerBoxBlockEntityMixin extends BlockEntity implements Taggable {
    private MutableText label = Text.literal("");
    private Item displayItem = Items.AIR;

    public ShulkerBoxBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(((ShulkerBoxBlockEntity) (Object) this));
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return ((ShulkerBoxBlockEntity) (Object) this).createNbt();
    }

    private void notifyClients(BlockState oldState) {
        ((ShulkerBoxBlockEntity) (Object) this).markDirty();
        if (world != null) world.updateListeners(this.pos, oldState, this.getCachedState(), Block.NOTIFY_LISTENERS);
    }

    @Override
    public Item getDisplayItem() {
        return displayItem;
    }

    @Override
    public void setDisplayItem(Item item) {
        BlockState oldState = this.getCachedState();

        displayItem = item;

        notifyClients(oldState);
    }

    @Override
    public MutableText getLabel() {
        return label;
    }

    @Override
    public void setLabel(MutableText newLabel) {
        BlockState oldState = this.getCachedState();

        label = newLabel;

        notifyClients(oldState);
    }

    @Inject(method = "writeNbt", at = @At("TAIL"))
    public void writeNbtMixin(NbtCompound nbt, CallbackInfo ci) {
        nbt.putString("label", label.getString());
        NbtCompound displayItemNbt = new NbtCompound();

        new ItemStack(displayItem).writeNbt(displayItemNbt);

        if (displayItem != null) {
            nbt.put("displayItem", displayItemNbt);
        }
    }

    @Inject(method = "readNbt", at = @At("TAIL"))
    public void readNbtMixin(NbtCompound nbt, CallbackInfo ci) {
        this.label = Text.of(nbt.getString("label")).copy();
        if (nbt.contains("displayItem")) {
            this.displayItem = ItemStack.fromNbt(nbt.getCompound("displayItem")).getItem();
        }
    }
}
