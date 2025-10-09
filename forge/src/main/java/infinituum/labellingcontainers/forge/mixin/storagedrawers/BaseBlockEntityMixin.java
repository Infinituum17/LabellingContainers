package infinituum.labellingcontainers.forge.mixin.storagedrawers;

import com.jaquadro.minecraft.storagedrawers.block.tile.BaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseBlockEntity.class)
public abstract class BaseBlockEntityMixin extends BlockEntity {
    public BaseBlockEntityMixin(BlockEntityType<?> arg, BlockPos arg2, BlockState arg3) {
        super(arg, arg2, arg3);
    }

    @Shadow
    protected abstract void saveAdditional(@NotNull CompoundTag tag);

    @Inject(method = "getUpdateTag", at = @At("RETURN"), cancellable = true)
    public void getUpdateTag(CallbackInfoReturnable<CompoundTag> cir) {
        CompoundTag tag = cir.getReturnValue();

        if(tag == null) {
            tag = new CompoundTag();
        }

        this.saveAdditional(tag);

        cir.setReturnValue(tag);
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    public void saveAdditional(CompoundTag tag, CallbackInfo ci) {
        super.saveAdditional(tag);
    }

    @Inject(method = "load", at = @At("TAIL"))
    public void load(CompoundTag tag, CallbackInfo ci) {
        super.load(tag);
    }
}
