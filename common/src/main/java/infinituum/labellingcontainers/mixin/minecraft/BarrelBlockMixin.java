package infinituum.labellingcontainers.mixin.minecraft;

import infinituum.labellingcontainers.utils.TaggableTooltip;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(BarrelBlock.class)
public class BarrelBlockMixin extends Block {
    public BarrelBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);

        tooltip.add(TaggableTooltip.get());
    }
}