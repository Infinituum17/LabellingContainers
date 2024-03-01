package infinituum.labellingcontainers.forge.mixin.colossalchests;

import infinituum.labellingcontainers.utils.TaggableTooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import org.cyclops.colossalchests.block.UncolossalChest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(UncolossalChest.class)
public class UncolossalChestMixin extends Block {
    public UncolossalChestMixin(Properties arg) {
        super(arg);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter world, @NotNull List<Component> tooltip, @NotNull TooltipFlag options) {
        super.appendHoverText(stack, world, tooltip, options);

        tooltip.add(TaggableTooltip.get());
    }
}
