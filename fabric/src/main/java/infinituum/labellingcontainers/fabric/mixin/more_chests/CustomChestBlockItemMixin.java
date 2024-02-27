package infinituum.labellingcontainers.fabric.mixin.more_chests;

import games.twinhead.morechests.item.CustomChestBlockItem;
import infinituum.labellingcontainers.utils.TaggableTooltip;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CustomChestBlockItem.class)
public class CustomChestBlockItemMixin {
    @Inject(method = "appendTooltip", at = @At("TAIL"))
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        tooltip.add(TaggableTooltip.get());
    }
}
