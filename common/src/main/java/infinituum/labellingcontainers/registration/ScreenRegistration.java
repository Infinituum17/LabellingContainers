package infinituum.labellingcontainers.registration;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import infinituum.labellingcontainers.screens.LabelPrinterScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;

public class ScreenRegistration {
    public static final DeferredRegister<ScreenHandlerType<?>> MENUS = DeferredRegister.create(MOD_ID, Registry.MENU_KEY);

    public static void init() {
        MENUS.register();
    }

    public static final RegistrySupplier<ScreenHandlerType<LabelPrinterScreenHandler>> LABEL_PRINTER_SCREEN_HANDLER =
            MENUS.register(new Identifier(MOD_ID, "label_printer_screen"), () -> new ScreenHandlerType<>(LabelPrinterScreenHandler::new));


}