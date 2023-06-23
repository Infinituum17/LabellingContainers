package infinituum.labellingcontainers.registration;

import infinituum.labellingcontainers.items.LabelPrinterItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static infinituum.labellingcontainers.LabellingContainers.MODID;

public class ItemRegistration {
    public static LabelPrinterItem LABEL_PRINTER = new LabelPrinterItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1));

    public static void init() {
        Registry.register(Registry.ITEM, new Identifier(MODID, "label_printer"), LABEL_PRINTER);
    }
}
