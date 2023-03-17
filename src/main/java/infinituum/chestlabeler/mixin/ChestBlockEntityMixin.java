package infinituum.chestlabeler.mixin;

import infinituum.chestlabeler.utils.Labelable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
public abstract class ChestBlockEntityMixin extends BlockEntity implements Labelable {
    private Text label = Text.literal("Chest");
    private Item displayItem = Items.CHEST;

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

    @Override
    public void setLabelDisplayItem(Item item) {
        BlockState oldState = this.getCachedState();

        displayItem = item;
        ((ChestBlockEntity)(Object)this).markDirty();

        if(world != null) world.updateListeners(this.pos, oldState, this.getCachedState(), Block.NOTIFY_LISTENERS);
    }

    @Override
    public void setLabel(Text newLabel) {
        BlockState oldState = this.getCachedState();

        label = newLabel;
        ((ChestBlockEntity)(Object)this).markDirty();

        if(world != null) world.updateListeners(this.pos, oldState, this.getCachedState(), Block.NOTIFY_LISTENERS);
    }

    @Nullable @Override
    public Item getLabelDisplayItem() {
        return displayItem;
    }

    @Override
    public Text getLabel() {
        return label;
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
        this.label = Text.of(nbt.getString("label"));
        if(nbt.contains("displayItem")) {
            this.displayItem = ItemStack.fromNbt(nbt.getCompound("displayItem")).getItem();
        }
    }
}
