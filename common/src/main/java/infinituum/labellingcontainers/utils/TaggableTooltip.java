package infinituum.labellingcontainers.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

public class TaggableTooltip {
    public static @NotNull Component get() {
        MutableComponent text = Component.literal("ⓘ ").withStyle(ChatFormatting.BLUE);
        text.append(Component.translatable("block.labelable").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        return text;
    }
}