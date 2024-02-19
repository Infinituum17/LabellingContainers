package infinituum.labellingcontainers.mixin;

import infinituum.labellingcontainers.utils.ChestHelper;
import infinituum.labellingcontainers.utils.TaggableChest;
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
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlockEntity.class)
public class ChestBlockEntityMixin extends BlockEntity implements TaggableChest {
    @Unique
    private MutableText labellingcontainers$label = Text.literal("");
    @Unique
    private Item labellingcontainers$displayItem = Items.AIR;

    public ChestBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    @Unique
    private void labellingcontainers$notifyClients(BlockState oldState) {
        this.markDirty();
        if (world != null) world.updateListeners(this.pos, oldState, this.getCachedState(), Block.NOTIFY_LISTENERS);
    }

    @Override
    public void labellingcontainers$setDisplayItem(Item item, boolean searchDoubleChest) {
        BlockState oldState = this.getCachedState();

        labellingcontainers$displayItem = item;
        if (searchDoubleChest) {
            TaggableChest otherChest = (TaggableChest) ChestHelper.getConnectedChestBlockEntity(world, pos, this.getCachedState());

            if (otherChest != null) {
                otherChest.labellingcontainers$setDisplayItem(item, false);
            }
        }

        labellingcontainers$notifyClients(oldState);
    }

    @Override
    public void labellingcontainers$setLabel(MutableText newLabel, boolean searchDoubleChest) {
        BlockState oldState = this.getCachedState();

        labellingcontainers$label = newLabel;
        if (searchDoubleChest) {
            TaggableChest otherChest = (TaggableChest) ChestHelper.getConnectedChestBlockEntity(world, pos, this.getCachedState());

            if (otherChest != null) {
                otherChest.labellingcontainers$setLabel(newLabel, false);
            }
        }

        labellingcontainers$notifyClients(oldState);
    }

    @Nullable
    @Override
    public Item labellingcontainers$getDisplayItem() {
        return labellingcontainers$displayItem;
    }

    @Override
    public void labellingcontainers$setDisplayItem(Item item) {
        labellingcontainers$setDisplayItem(item, true);
    }

    @Override
    public MutableText labellingcontainers$getLabel() {
        return labellingcontainers$label;
    }

    @Override
    public void labellingcontainers$setLabel(MutableText newLabel) {
        labellingcontainers$setLabel(newLabel, true);
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
