package infinituum.labellingcontainers.fabric.mixin.compact_storage;

import com.tabithastrong.compactstorage.block.CompactBarrelBlock;
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

@Mixin(CompactBarrelBlock.class)
public class CompactBarrelBlockMixin extends Block {
    public CompactBarrelBlockMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "appendHoverText", at = @At("TAIL"))
    public void appendTooltip(ItemStack stack, @Nullable BlockGetter world, List<Component> tooltip, TooltipFlag options, CallbackInfo ci) {
        tooltip.add(TaggableTooltip.get());
    }
}
