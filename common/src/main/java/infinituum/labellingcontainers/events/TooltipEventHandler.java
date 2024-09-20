package infinituum.labellingcontainers.events;

import infinituum.fastconfigapi.FastConfigs;
import infinituum.labellingcontainers.config.CompatibleContainers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public final class TooltipEventHandler {
    public static void handle(ItemStack itemStack, List<Component> components, TooltipFlag tooltipFlag) {
        ResourceLocation resLoc = itemStack.getItem().arch$registryName();

        if (resLoc == null) {
            return;
        }

        CompatibleContainers config = FastConfigs.get(CompatibleContainers.class);

        Block block = Block.byItem(itemStack.getItem());
        BlockState blockState = block.defaultBlockState();

        if (config.isLimited() && (config.has(resLoc.toString()) || config.hasAnyTag(blockState.getTags()))) {
            MutableComponent text = Component.literal("ⓘ ").withStyle(ChatFormatting.BLUE);
            text.append(Component.translatable("block.labelable").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));

            components.add(text);
        }
    }
}
