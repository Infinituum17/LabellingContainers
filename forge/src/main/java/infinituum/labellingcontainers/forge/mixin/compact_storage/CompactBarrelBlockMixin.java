package infinituum.labellingcontainers.forge.mixin.compact_storage;

import com.tabithastrong.compactstorage.block.CompactBarrelBlock;
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

@Mixin(CompactBarrelBlock.class)
public class CompactBarrelBlockMixin extends Block {
    public CompactBarrelBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "appendTooltip", at = @At("TAIL"))
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options, CallbackInfo ci) {
        tooltip.add(TaggableTooltip.get());
    }
}
