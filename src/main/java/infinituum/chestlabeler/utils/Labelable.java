package infinituum.chestlabeler.utils;


import net.minecraft.item.Item;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public interface Labelable {
    @Nullable Item getLabelDisplayItem();
    Text getLabel();
    void setLabel(Text newLabel);
}
