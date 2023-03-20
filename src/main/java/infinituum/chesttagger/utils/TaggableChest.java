package infinituum.chesttagger.utils;

import net.minecraft.item.Item;
import net.minecraft.text.MutableText;

public interface TaggableChest extends Taggable {
    void setDisplayItem(Item item, boolean searchDoubleChest);
    void setLabel(MutableText newLabel, boolean searchDoubleChest);
}
