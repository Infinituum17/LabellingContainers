package infinituum.labellingcontainers.mixin;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
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
        MutableText text = Text.literal("ⓘ ").formatted(Formatting.BLUE);
        text.append(Text.translatable("block.labelable").formatted(Formatting.ITALIC).formatted(Formatting.GRAY));
        tooltip.add(text);

        super.appendTooltip(stack, world, tooltip, options);
    }
}
