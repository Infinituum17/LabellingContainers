package infinituum.labellingcontainers.mixin.minecraft;

import infinituum.labellingcontainers.utils.TaggableTooltip;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Block;

@Mixin(BarrelBlock.class)
public class BarrelBlockMixin extends Block {
    public BarrelBlockMixin(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter world, List<Component> tooltip, TooltipFlag options) {
        super.appendHoverText(stack, world, tooltip, options);

        tooltip.add(TaggableTooltip.get());
    }
}
