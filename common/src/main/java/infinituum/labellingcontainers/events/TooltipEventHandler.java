package infinituum.labellingcontainers.events;

import infinituum.labellingcontainers.config.TaggableBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

import static infinituum.labellingcontainers.LabellingContainersConfig.TAGGABLE_BLOCKS_CONFIG;

public class TooltipEventHandler {
    public static void handle(ItemStack itemStack, List<Component> components, TooltipFlag tooltipFlag) {
        ResourceLocation resLoc = itemStack.getItem().arch$registryName();

        if (resLoc == null) return;

        TaggableBlocks config = TAGGABLE_BLOCKS_CONFIG.getConfig();
        Block block = Block.byItem(itemStack.getItem());
        BlockState blockState = block.defaultBlockState();

        if (config.isLimited() && (config.has(resLoc.toString()) || config.hasAnyTag(blockState.getTags()))) {

            MutableComponent text = new TextComponent("ⓘ ").withStyle(ChatFormatting.BLUE);
            text.append(new TranslatableComponent("block.labelable").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));

            components.add(text);
        }
    }
}
