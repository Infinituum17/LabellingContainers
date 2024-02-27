package infinituum.labellingcontainers.forge.mixin.compact_storage;

import com.tabithastrong.compactstorage.block.CompactChestBlock;
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

@Mixin(CompactChestBlock.class)
public class CompactChestBlockMixin extends Block {
    public CompactChestBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "appendTooltip", at = @At("TAIL"))
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options, CallbackInfo ci) {
        tooltip.add(TaggableTooltip.get());
    }
}
