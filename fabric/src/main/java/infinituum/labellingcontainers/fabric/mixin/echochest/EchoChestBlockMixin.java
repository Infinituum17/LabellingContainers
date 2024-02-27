package infinituum.labellingcontainers.fabric.mixin.echochest;

import fuzs.echochest.world.level.block.EchoChestBlock;
import infinituum.labellingcontainers.utils.TaggableTooltip;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EchoChestBlock.class)
public class EchoChestBlockMixin extends Block {
    public EchoChestBlockMixin(Settings arg) {
        super(arg);
    }

    @Inject(method = "appendTooltip", at = @At("TAIL"))
    public void appendTooltip(ItemStack stack, @Nullable BlockView level, List<Text> tooltip, TooltipContext flag, CallbackInfo ci) {
        tooltip.add(TaggableTooltip.get());
    }
}
