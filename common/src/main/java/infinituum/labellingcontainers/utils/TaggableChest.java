package infinituum.labellingcontainers.utils;

import net.minecraft.item.Item;
import net.minecraft.text.MutableText;

public interface TaggableChest extends Taggable {
    void labellingcontainers$setDisplayItem(Item item, boolean searchDoubleChest);

    void labellingcontainers$setLabel(MutableText newLabel, boolean searchDoubleChest);
}
