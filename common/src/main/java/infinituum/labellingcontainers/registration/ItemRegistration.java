package infinituum.labellingcontainers.registration;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import infinituum.labellingcontainers.items.LabelPrinterItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;

public final class ItemRegistration {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    public static final ResourceKey<Item> LABEL_PRINTER_RESOURCE_KEY = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, "label_printer"));
    public static final RegistrySupplier<Item> LABEL_PRINTER = ITEMS.register(LABEL_PRINTER_RESOURCE_KEY.location(),
            () -> new LabelPrinterItem(
                    new Item.Properties()
                            .stacksTo(1)
                            .setId(LABEL_PRINTER_RESOURCE_KEY)
                            .arch$tab(CreativeModeTabs.TOOLS_AND_UTILITIES)));

    public static void init() {
        ITEMS.register();
    }
}