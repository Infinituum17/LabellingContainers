package infinituum.labellingcontainers.forge.mixin.colossalchests;

import infinituum.labellingcontainers.utils.TaggableTooltip;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.BlockView;
import org.cyclops.colossalchests.block.ColossalChest;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(ColossalChest.class)
public class ColossalChestMixin extends Block {
    public ColossalChestMixin(Settings arg) {
        super(arg);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);

        tooltip.add(TaggableTooltip.get());
    }
}
