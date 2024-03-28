package infinituum.labellingcontainers.events;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

import static infinituum.labellingcontainers.LabellingContainersConfig.TAGGABLE_BLOCKS_CONFIG;

public class TooltipEventHandler {
    public static void handle(ItemStack itemStack, List<Component> components, TooltipFlag tooltipFlag) {
        ResourceLocation resLoc = itemStack.getItem().arch$registryName();

        if (resLoc != null && TAGGABLE_BLOCKS_CONFIG.get().isLimited() && TAGGABLE_BLOCKS_CONFIG.get().hasId(resLoc.toString())) {
            MutableComponent text = Component.literal("ⓘ ").withStyle(ChatFormatting.BLUE);
            text.append(Component.translatable("block.labelable").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));

            components.add(text);
        }
    }
}