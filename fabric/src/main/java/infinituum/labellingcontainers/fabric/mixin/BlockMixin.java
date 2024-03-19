package infinituum.labellingcontainers.fabric.mixin;

import infinituum.labellingcontainers.fabric.events.BlockPlaceCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockMixin {
    @Inject(method = "place", at = @At("RETURN"))
    public void setPlacedBy(BlockPlaceContext blockPlaceContext, CallbackInfoReturnable<InteractionResult> cir) {
        BlockPlaceCallback.EVENT.invoker().interact(
                blockPlaceContext.getPlayer(),
                blockPlaceContext.getLevel(),
                blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos()),
                blockPlaceContext.getClickedPos()
        );
    }
}
