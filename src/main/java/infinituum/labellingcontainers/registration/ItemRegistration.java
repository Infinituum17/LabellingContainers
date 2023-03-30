package infinituum.labellingcontainers.registration;

import infinituum.labellingcontainers.items.LabelPrinterItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static infinituum.labellingcontainers.LabellingContainers.MODID;

public class ItemRegistration {
    public static LabelPrinterItem LABEL_PRINTER = new LabelPrinterItem(new FabricItemSettings());

    @SuppressWarnings("UnstableApiUsage")
    public static void init() {
        Registry.register(Registries.ITEM, new Identifier(MODID, "label_printer"), LABEL_PRINTER);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content
                .addAfter(Items.NAME_TAG, LABEL_PRINTER)
        );
    }
}
