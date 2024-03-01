package infinituum.labellingcontainers.fabric.mixin.echochest;

import fuzs.echochest.world.level.block.EchoChestBlock;
import infinituum.labellingcontainers.utils.TaggableTooltip;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;

@Mixin(EchoChestBlock.class)
public class EchoChestBlockMixin extends Block {
    public EchoChestBlockMixin(Properties arg) {
        super(arg);
    }

    @Inject(method = "appendHoverText", at = @At("TAIL"))
    public void appendTooltip(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag, CallbackInfo ci) {
        tooltip.add(TaggableTooltip.get());
    }
}
