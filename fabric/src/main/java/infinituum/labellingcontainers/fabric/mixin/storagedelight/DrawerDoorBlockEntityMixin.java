package infinituum.labellingcontainers.fabric.mixin.storagedelight;

import com.axperty.storagedelight.block.entity.DrawerDoorBlockEntity;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DrawerDoorBlockEntity.class)
public abstract class DrawerDoorBlockEntityMixin {
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
