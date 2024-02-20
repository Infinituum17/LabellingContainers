package infinituum.labellingcontainers.registration;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import infinituum.labellingcontainers.items.LabelPrinterItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;

public class ItemRegistration {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registry.ITEM_KEY);
    public static final RegistrySupplier<Item> LABEL_PRINTER = ITEMS.register("label_printer", () ->
            new LabelPrinterItem(new Item.Settings().maxCount(1).group(ItemGroup.TOOLS)));

    public static void init() {
        ITEMS.register();
    }
}
