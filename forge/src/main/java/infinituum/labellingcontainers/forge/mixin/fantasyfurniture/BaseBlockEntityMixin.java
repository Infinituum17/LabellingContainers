package infinituum.labellingcontainers.forge.mixin.fantasyfurniture;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.apex.forge.apexcore.lib.block.entity.BaseBlockEntity;

@Mixin(BaseBlockEntity.class)
public abstract class BaseBlockEntityMixin extends BlockEntity {
    public BaseBlockEntityMixin(BlockEntityType<?> arg, BlockPos arg2, BlockState arg3) {
        super(arg, arg2, arg3);
    }

    @Inject(method = "saveAdditional", at = @At("HEAD"))
    public void saveAdditional(CompoundTag tagCompound, CallbackInfo ci) {
        super.saveAdditional(tagCompound);
    }
}
