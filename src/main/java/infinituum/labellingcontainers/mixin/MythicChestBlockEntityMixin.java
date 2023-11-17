package infinituum.labellingcontainers.mixin;

import infinituum.labellingcontainers.utils.ChestHelper;
import infinituum.labellingcontainers.utils.TaggableChest;
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
import nourl.mythicmetalsdecorations.blocks.chest.MythicChestBlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MythicChestBlockEntity.class)
public class MythicChestBlockEntityMixin extends BlockEntity implements TaggableChest {
    private MutableText label = Text.literal("");
    private Item displayItem = Items.AIR;

    public MythicChestBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create((MythicChestBlockEntity)(Object)this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return super.createNbt();
    }

    private void notifyClients(BlockState oldState) {
        super.markDirty();
        if(world != null) world.updateListeners(this.pos, oldState, this.getCachedState(), Block.NOTIFY_LISTENERS);
    }

    @Override
    public Item getDisplayItem() {
        return displayItem;
    }

    @Override
    public void setDisplayItem(Item item) {
        setDisplayItem(item, true);
    }

    @Override
    public void setDisplayItem(Item item, boolean searchDoubleChest) {
        BlockState oldState = this.getCachedState();

        displayItem = item;
        if(searchDoubleChest) {
            TaggableChest otherChest = (TaggableChest) ChestHelper.getConnectedChestBlockEntity(world, pos, this.getCachedState());

            if (otherChest != null) {
                otherChest.setDisplayItem(item, false);
            }
        }

        notifyClients(oldState);
    }

    @Override
    public MutableText getLabel() {
        return label;
    }

    @Override
    public void setLabel(MutableText newLabel) {
        setLabel(newLabel, true);
    }

    @Override
    public void setLabel(MutableText newLabel, boolean searchDoubleChest) {
        BlockState oldState = this.getCachedState();

        label = newLabel;
        if(searchDoubleChest) {
            TaggableChest otherChest = (TaggableChest) ChestHelper.getConnectedChestBlockEntity(world, pos, this.getCachedState());

            if (otherChest != null) {
                otherChest.setLabel(newLabel, false);
            }
        }

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
