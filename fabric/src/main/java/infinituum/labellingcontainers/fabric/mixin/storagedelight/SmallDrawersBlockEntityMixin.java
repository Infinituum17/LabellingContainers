package infinituum.labellingcontainers.fabric.mixin.storagedelight;

import com.axperty.storagedelight.block.entity.SmallDrawersBlockEntity;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SmallDrawersBlockEntity.class)
public abstract class SmallDrawersBlockEntityMixin {
    @Shadow
    public abstract void saveAdditional(CompoundTag tag);

    @Inject(method = "getUpdateTag", at = @At("HEAD"), cancellable = true)
    public void getUpdateTag(CallbackInfoReturnable<CompoundTag> cir) {
        CompoundTag tag = cir.getReturnValue();

        if(tag == null) {
            tag = new CompoundTag();
        }

        this.saveAdditional(tag);

        cir.setReturnValue(tag);
    }
}
