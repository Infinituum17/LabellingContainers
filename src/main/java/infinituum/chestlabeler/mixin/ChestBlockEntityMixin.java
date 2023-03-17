package infinituum.chestlabeler.mixin;

import infinituum.chestlabeler.utils.Labelable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("")
@Mixin(ChestBlockEntity.class)
public class ChestBlockEntityMixin extends BlockEntity implements Labelable {
    private Text label = Text.literal("Chest");

    public ChestBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(((ChestBlockEntity)(Object)this));
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return ((ChestBlockEntity)(Object)this).createNbt();
    }

    @Nullable @Override
    public Item getLabelDisplayItem() {
        return null;
    }

    @Override
    public Text getLabel() {
        return label;
    }

    @Override
    public void setLabel(Text newLabel) {
        BlockState oldState = this.getCachedState();

        label = newLabel;
        ((ChestBlockEntity)(Object)this).markDirty();

        if(world != null) world.updateListeners(this.pos, oldState, this.getCachedState(), Block.NOTIFY_LISTENERS);
    }

    @Inject(method = "writeNbt", at = @At("TAIL"))
    public void writeNbtMixin(NbtCompound nbt, CallbackInfo ci) {
        nbt.putString("label", label.getString());
    }

    @Inject(method = "readNbt", at = @At("TAIL"))
    public void readNbtMixin(NbtCompound nbt, CallbackInfo ci) {
        this.label = Text.of(nbt.getString("label"));
    }
}
