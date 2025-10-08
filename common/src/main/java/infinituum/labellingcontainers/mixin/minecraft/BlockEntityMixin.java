package infinituum.labellingcontainers.mixin.minecraft;

import infinituum.labellingcontainers.utils.BlockEntityHelper;
import infinituum.labellingcontainers.utils.Taggable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

import static com.mojang.text2speech.Narrator.LOGGER;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin implements Taggable {
    @Shadow
    @Nullable
    protected Level level;
    @Shadow
    @Final
    protected BlockPos worldPosition;
    @Unique
    private MutableComponent labellingcontainers$label = Component.literal("");
    @Unique
    private Item labellingcontainers$displayItem = Items.AIR;

    @Shadow
    public abstract BlockState getBlockState();

    @Shadow
    public abstract void setChanged();

    @Shadow protected abstract void saveAdditional(ValueOutput valueOutput);

    @Shadow public abstract ProblemReporter.PathElement problemPath();

    @Unique
    private void labellingcontainers$notifyClients(BlockState oldState) {
        this.setChanged();

        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, oldState, this.getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public Item labellingcontainers$getDisplayItem() {
        return labellingcontainers$displayItem;
    }

    @Override
    public void labellingcontainers$setDisplayItem(Item item, boolean searchDoubleChest) {
        BlockState oldState = this.getBlockState();

        labellingcontainers$displayItem = item;

        if (searchDoubleChest && level != null) {
            Taggable otherChest = (Taggable) BlockEntityHelper.locateTargetBlockEntity(level, worldPosition, this.getBlockState());

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
    public void labellingcontainers$setLabel(MutableComponent newLabel, boolean searchDoubleChest) {
        BlockState oldState = this.getBlockState();

        labellingcontainers$label = newLabel;

        if (searchDoubleChest && level != null) {
            Taggable otherChest = (Taggable) BlockEntityHelper.locateTargetBlockEntity(level, worldPosition, this.getBlockState());

            if (otherChest != null) {
                otherChest.labellingcontainers$setLabel(newLabel, false);
            }
        }

        labellingcontainers$notifyClients(oldState);
    }

    @Inject(method = "getUpdatePacket", at = @At("RETURN"), cancellable = true)
    public void getUpdatePacket(CallbackInfoReturnable<Packet<ClientGamePacketListener>> cir) {
        if (cir.getReturnValue() == null) {
            cir.setReturnValue(ClientboundBlockEntityDataPacket.create((BlockEntity) (Object) this));
            return;
        }

        if (cir.getReturnValue() instanceof ClientboundBlockEntityDataPacket packet) {
            if (packet.getTag().isEmpty()) {
                cir.setReturnValue(ClientboundBlockEntityDataPacket.create((BlockEntity) (Object) this));
            }
        }
    }

    @Inject(method = "getUpdateTag", at = @At("RETURN"), cancellable = true)
    public void getUpdateTag(HolderLookup.Provider registries, CallbackInfoReturnable<CompoundTag> cir) {
        CompoundTag tag = cir.getReturnValue();

        try (ProblemReporter.ScopedCollector scopedCollector = new ProblemReporter.ScopedCollector(this.problemPath(), LOGGER)) {
            TagValueOutput output = TagValueOutput.createWithContext(scopedCollector, registries);

            this.saveAdditional(output);

            CompoundTag result = output.buildResult();

            cir.setReturnValue(result.merge(tag));
        }
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    public void saveAdditional(ValueOutput output, CallbackInfo ci) {
        output.putString("label", labellingcontainers$label.getString());
        ItemStack itemStack = new ItemStack(labellingcontainers$displayItem);

        if (labellingcontainers$displayItem != null && !itemStack.isEmpty()) {
            output.store("displayItem", ItemStack.SIMPLE_ITEM_CODEC, itemStack);
        }
    }

    @Inject(method = "loadAdditional", at = @At("TAIL"))
    public void loadAdditional(ValueInput input, CallbackInfo ci) {
        this.labellingcontainers$label = Component.nullToEmpty(input.getString("label").orElse("")).copy();

        Optional<ItemStack> item = input.read("displayItem", ItemStack.SIMPLE_ITEM_CODEC);

        item.ifPresent(itemStack -> this.labellingcontainers$displayItem = itemStack.getItem());
    }
}
