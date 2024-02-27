package infinituum.labellingcontainers.mixin.minecraft;

import infinituum.labellingcontainers.utils.TaggableTooltip;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxBlockMixin extends Block {
    public ShulkerBoxBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "appendTooltip", at = @At("TAIL"))
    public void appendTooltip(ItemStack stack, BlockView world, List<Text> tooltip, TooltipContext options, CallbackInfo ci) {
        super.appendTooltip(stack, world, tooltip, options);

        tooltip.add(TaggableTooltip.get());
    }
}