package infinituum.labellingcontainers.fabric.mixin.more_chests;

import games.twinhead.morechests.item.CustomChestBlockItem;
import infinituum.labellingcontainers.utils.TaggableTooltip;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

@Mixin(CustomChestBlockItem.class)
public class CustomChestBlockItemMixin {
    @Inject(method = "appendHoverText", at = @At("TAIL"))
    public void appendTooltip(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag context, CallbackInfo ci) {
        tooltip.add(TaggableTooltip.get());
    }
}
