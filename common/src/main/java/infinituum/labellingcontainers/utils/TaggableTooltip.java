package infinituum.labellingcontainers.utils;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class TaggableTooltip {
    public static Text get() {
        MutableText text = Text.literal("ⓘ ").formatted(Formatting.BLUE);
        text.append(Text.translatable("block.labelable").formatted(Formatting.ITALIC).formatted(Formatting.GRAY));
        return text;
    }
}
