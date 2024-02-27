package infinituum.labellingcontainers.forge.mixin.compact_storage;

import com.witchica.compactstorage.common.block.CompactBarrelBlock;
import infinituum.labellingcontainers.utils.TaggableTooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CompactBarrelBlock.class)
public class CompactBarrelBlockMixin extends Block {

    public CompactBarrelBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "appendHoverText", at = @At("TAIL"))
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter world, List<Component> tooltip, TooltipFlag options, CallbackInfo ci) {
        tooltip.add(TaggableTooltip.get());
    }
}
