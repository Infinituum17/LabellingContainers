package infinituum.labellingcontainers.registration;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import infinituum.labellingcontainers.screens.LabelPrinterScreenHandler;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;

public class ScreenRegistration {
    public static final DeferredRegister<ScreenHandlerType<?>> MENUS = DeferredRegister.create(MOD_ID, RegistryKeys.SCREEN_HANDLER);

    public static void init() {
        MENUS.register();
    }

    public static final RegistrySupplier<ScreenHandlerType<LabelPrinterScreenHandler>> LABEL_PRINTER_SCREEN_HANDLER =
            MENUS.register(new Identifier(MOD_ID, "label_printer_screen"), () -> new ScreenHandlerType<>(LabelPrinterScreenHandler::new));


}