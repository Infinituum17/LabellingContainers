package infinituum.labellingcontainers.forge.mixin.ironchests;

import infinituum.labellingcontainers.utils.TaggableTooltip;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import tech.thatgravyboat.ironchests.common.blocks.GenericChestBlock;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;

@Mixin(GenericChestBlock.class)
public class GenericChestBlockMixin extends Block {
    public GenericChestBlockMixin(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter world, @NotNull List<Component> tooltip, @NotNull TooltipFlag options) {
        super.appendHoverText(stack, world, tooltip, options);

        tooltip.add(TaggableTooltip.get());
    }
}