package infinituum.labellingcontainers.registration;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import infinituum.labellingcontainers.items.LabelPrinterItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;

public class ItemRegistration {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    public static final RegistrySupplier<Item> LABEL_PRINTER = ITEMS.register("label_printer", () ->
            new LabelPrinterItem(new Item.Properties().stacksTo(1).arch$tab(CreativeModeTabs.TOOLS_AND_UTILITIES)));

    public static void init() {
        ITEMS.register();
    }
}
