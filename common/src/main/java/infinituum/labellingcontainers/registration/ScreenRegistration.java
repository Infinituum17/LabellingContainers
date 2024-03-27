package infinituum.labellingcontainers.registration;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import infinituum.labellingcontainers.screens.LabelPrinterScreenHandler;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;

public class ScreenRegistration {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(MOD_ID, Registry.MENU_REGISTRY);

    public static void init() {
        MENUS.register();
    }

    public static final RegistrySupplier<MenuType<LabelPrinterScreenHandler>> LABEL_PRINTER_SCREEN_HANDLER =
            MENUS.register(new ResourceLocation(MOD_ID, "label_printer_screen"), () -> new MenuType<>(LabelPrinterScreenHandler::new));
}