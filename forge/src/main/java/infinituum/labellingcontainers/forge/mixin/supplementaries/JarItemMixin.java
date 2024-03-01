package infinituum.labellingcontainers.forge.mixin.supplementaries;

import infinituum.labellingcontainers.utils.TaggableTooltip;
import net.mehvahdjukaar.supplementaries.common.items.JarItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(JarItem.class)
public class JarItemMixin {
    @Inject(method = "appendHoverText", at = @At("RETURN"))
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn, CallbackInfo ci) {
        tooltip.add(TaggableTooltip.get());
    }
}
