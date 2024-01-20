package infinituum.labellingcontainers;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

public class LabellingContainers {
    public static final String MOD_ID = "labellingcontainers";
    // We can use this if we don't want to use DeferredRegister
    // public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    // Registering a new creative tab
    public static final DeferredRegister<ItemGroup> TABS = DeferredRegister.create(MOD_ID, RegistryKeys.ITEM_GROUP);
    public static final RegistrySupplier<ItemGroup> EXAMPLE_TAB = TABS.register("example_tab", () ->
            CreativeTabRegistry.create(Text.translatable("itemGroup." + MOD_ID + ".example_tab"),
                    () -> new ItemStack(LabellingContainers.EXAMPLE_ITEM.get())));
    
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, RegistryKeys.ITEM);
    public static final RegistrySupplier<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () ->
            new Item(new Item.Settings().arch$tab(LabellingContainers.EXAMPLE_TAB)));
    
    public static void init() {
        TABS.register();
        ITEMS.register();
        
        System.out.println(LCExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}
