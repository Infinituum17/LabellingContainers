package infinituum.labellingcontainers.fabric.mixin.supplementaries;

import infinituum.labellingcontainers.utils.TaggableTooltip;
import net.mehvahdjukaar.supplementaries.common.block.blocks.AbstractPresentBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(AbstractPresentBlock.class)
public class AbstractPresentBlockMixin extends Block {
    public AbstractPresentBlockMixin(Properties arg) {
        super(arg);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter world, @NotNull List<Component> tooltip, @NotNull TooltipFlag options) {
        super.appendHoverText(stack, world, tooltip, options);

        tooltip.add(TaggableTooltip.get());
    }
}
