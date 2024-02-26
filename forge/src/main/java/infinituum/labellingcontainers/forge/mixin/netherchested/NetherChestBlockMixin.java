package infinituum.labellingcontainers.forge.mixin.netherchested;

import fuzs.netherchested.world.level.block.NetherChestBlock;
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

@Mixin(NetherChestBlock.class)
public class NetherChestBlockMixin extends Block {
    public NetherChestBlockMixin(Settings arg) {
        super(arg);
    }

    @Inject(method = "appendTooltip", at = @At("TAIL"))
    public void appendTooltip(ItemStack stack, @Nullable BlockView level, List<Text> tooltip, TooltipContext flag, CallbackInfo ci) {
        tooltip.add(TaggableTooltip.get());
    }
}
