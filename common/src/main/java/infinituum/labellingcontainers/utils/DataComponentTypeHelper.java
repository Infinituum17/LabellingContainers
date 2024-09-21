package infinituum.labellingcontainers.utils;

import infinituum.labellingcontainers.registration.DataComponentRegistration;
import infinituum.labellingcontainers.registration.component_types.LabelProperties;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.world.item.ItemStack;

public final class DataComponentTypeHelper {
    public static LabelProperties getLabelProperties(DataComponentHolder holder) {
        return holder.getOrDefault(
                DataComponentRegistration.LABEL_COMPONENT_TYPE.get(),
                new LabelProperties("", ItemStack.EMPTY, 0)
        );
    }
}
