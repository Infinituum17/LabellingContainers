package infinituum.labellingcontainers.utils;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;

public interface TaggableChest extends Taggable {
    void labellingcontainers$setDisplayItem(Item item, boolean searchDoubleChest);

    void labellingcontainers$setLabel(MutableComponent newLabel, boolean searchDoubleChest);
}
