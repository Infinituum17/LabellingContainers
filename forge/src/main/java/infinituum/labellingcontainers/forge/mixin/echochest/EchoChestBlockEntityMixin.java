package infinituum.labellingcontainers.forge.mixin.echochest;

import fuzs.echochest.world.level.block.entity.EchoChestBlockEntity;
import infinituum.labellingcontainers.utils.Taggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EchoChestBlockEntity.class)
public class EchoChestBlockEntityMixin extends BlockEntity implements Taggable {
    @Unique
    private MutableText labellingcontainers$label = Text.literal("");
    @Unique
    private Item labellingcontainers$displayItem = Items.AIR;

    public EchoChestBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Unique
    private void labellingcontainers$notifyClients(BlockState oldState) {
        this.markDirty();
        if (world != null) world.updateListeners(this.pos, oldState, this.getCachedState(), Block.NOTIFY_LISTENERS);
    }

    @Override
    public Item labellingcontainers$getDisplayItem() {
        return labellingcontainers$displayItem;
    }

    @Override
    public void labellingcontainers$setDisplayItem(Item item) {
        BlockState oldState = this.getCachedState();

        labellingcontainers$displayItem = item;

        labellingcontainers$notifyClients(oldState);
    }

    @Override
    public MutableText labellingcontainers$getLabel() {
        return labellingcontainers$label;
    }

    @Override
    public void labellingcontainers$setLabel(MutableText newLabel) {
        BlockState oldState = this.getCachedState();

        labellingcontainers$label = newLabel;

        labellingcontainers$notifyClients(oldState);
    }

    @Inject(method = "writeNbt", at = @At("TAIL"))
    public void writeNbtMixin(NbtCompound nbt, CallbackInfo ci) {
        nbt.putString("label", labellingcontainers$label.getString());
        NbtCompound displayItemNbt = new NbtCompound();

        new ItemStack(labellingcontainers$displayItem).writeNbt(displayItemNbt);

        if (labellingcontainers$displayItem != null) {
            nbt.put("displayItem", displayItemNbt);
        }
    }

    @Inject(method = "readNbt", at = @At("TAIL"))
    public void readNbtMixin(NbtCompound nbt, CallbackInfo ci) {
        this.labellingcontainers$label = Text.of(nbt.getString("label")).copy();
        if (nbt.contains("displayItem")) {
            this.labellingcontainers$displayItem = ItemStack.fromNbt(nbt.getCompound("displayItem")).getItem();
        }
    }
}
