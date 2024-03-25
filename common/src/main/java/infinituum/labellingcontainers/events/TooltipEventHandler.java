package infinituum.labellingcontainers.events;

import infinituum.labellingcontainers.utils.TaggableTooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

import static infinituum.labellingcontainers.LabellingContainersConfig.TAGGABLE_BLOCKS_CONFIG;

public class TooltipEventHandler {
    public static void handle(ItemStack itemStack, List<Component> components, TooltipFlag tooltipFlag) {
        ResourceLocation resLoc = itemStack.getItem().arch$registryName();

        if (resLoc != null && TAGGABLE_BLOCKS_CONFIG.get().hasTagsLimit && TAGGABLE_BLOCKS_CONFIG.get().taggableIds.contains(resLoc.toString())) {
            components.add(TaggableTooltip.get());
        }
    }
}
