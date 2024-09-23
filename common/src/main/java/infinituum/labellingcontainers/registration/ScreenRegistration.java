package infinituum.labellingcontainers.registration;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import infinituum.labellingcontainers.screens.LabelPrinterMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;

public final class ScreenRegistration {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(MOD_ID, Registries.MENU);

    public static void init() {
        MENUS.register();
    }

    public static final RegistrySupplier<MenuType<LabelPrinterMenu>> LABEL_PRINTER_SCREEN = MENUS.register("label_printer_screen", () -> new MenuType<>(LabelPrinterMenu::new, FeatureFlagSet.of()));
}