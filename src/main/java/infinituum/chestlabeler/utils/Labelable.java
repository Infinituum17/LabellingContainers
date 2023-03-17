package infinituum.chestlabeler.utils;


import net.minecraft.item.Item;
import net.minecraft.text.Text;

public interface Labelable {
    Item getLabelDisplayItem();
    void setLabelDisplayItem(Item item);
    Text getLabel();
    void setLabel(Text newLabel);
}
