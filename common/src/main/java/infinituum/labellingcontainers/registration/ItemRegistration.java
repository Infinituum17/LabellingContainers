package infinituum.labellingcontainers.registration;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import infinituum.labellingcontainers.items.LabelPrinterItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.RegistryKeys;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;

public class ItemRegistration {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, RegistryKeys.ITEM);
    public static final RegistrySupplier<Item> LABEL_PRINTER = ITEMS.register("label_printer", () ->
            new LabelPrinterItem(new Item.Settings().maxCount(1).arch$tab(ItemGroups.TOOLS)));

    public static void init() {
        ITEMS.register();
    }
}
