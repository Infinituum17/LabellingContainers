package infinituum.labellingcontainers.utils;


import net.minecraft.item.Item;
import net.minecraft.text.MutableText;

public interface Taggable {
    Item labellingcontainers$getDisplayItem();

    void labellingcontainers$setDisplayItem(Item item);

    MutableText labellingcontainers$getLabel();

    void labellingcontainers$setLabel(MutableText newLabel);
}
