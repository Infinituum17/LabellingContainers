package infinituum.labellingcontainers.registration;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import infinituum.labellingcontainers.screens.LabelPrinterScreenHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;
import static infinituum.labellingcontainers.utils.CommonHelper.id;

public final class ScreenRegistration {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(MOD_ID, Registries.MENU);

    public static void init() {
        MENUS.register();
    }

    public static final RegistrySupplier<MenuType<LabelPrinterScreenHandler>> LABEL_PRINTER_SCREEN_HANDLER =
            MENUS.register(id("label_printer_screen"), () -> new MenuType<>(LabelPrinterScreenHandler::new, FeatureFlagSet.of()));
}