package infinituum.chesttagger.utils;


import net.minecraft.item.Item;
import net.minecraft.text.MutableText;

public interface Taggable {
    Item getDisplayItem();
    void setDisplayItem(Item item);
    MutableText getLabel();
    void setLabel(MutableText newLabel);
}
