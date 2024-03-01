package infinituum.labellingcontainers.utils;


import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;

public interface Taggable {
    Item labellingcontainers$getDisplayItem();

    void labellingcontainers$setDisplayItem(Item item);

    MutableComponent labellingcontainers$getLabel();

    void labellingcontainers$setLabel(MutableComponent newLabel);
}
