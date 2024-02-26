package infinituum.labellingcontainers.forge.mixin.ironchests;

import infinituum.labellingcontainers.utils.TaggableTooltip;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import tech.thatgravyboat.ironchests.common.blocks.GenericChestBlock;

import java.util.List;

@Mixin(GenericChestBlock.class)
public class GenericChestBlockMixin extends Block {
    public GenericChestBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);

        tooltip.add(TaggableTooltip.get());
    }
}