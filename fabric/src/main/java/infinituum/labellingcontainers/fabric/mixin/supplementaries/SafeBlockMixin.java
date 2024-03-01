package infinituum.labellingcontainers.fabric.mixin.supplementaries;

import infinituum.labellingcontainers.utils.TaggableTooltip;
import net.mehvahdjukaar.supplementaries.common.block.blocks.SafeBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(SafeBlock.class)
public class SafeBlockMixin {
    @Inject(method = "appendHoverText", at = @At("TAIL"))
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flagIn, CallbackInfo ci) {
        tooltip.add(TaggableTooltip.get());
    }
}
