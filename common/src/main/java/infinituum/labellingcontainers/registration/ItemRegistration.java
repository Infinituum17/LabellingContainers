package infinituum.labellingcontainers.registration;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import infinituum.labellingcontainers.items.LabelPrinterItem;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;

public class ItemRegistration {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registry.ITEM_REGISTRY);
    public static final RegistrySupplier<Item> LABEL_PRINTER = ITEMS.register("label_printer", () ->
            new LabelPrinterItem(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_TOOLS)));

    public static void init() {
        ITEMS.register();
    }
}
