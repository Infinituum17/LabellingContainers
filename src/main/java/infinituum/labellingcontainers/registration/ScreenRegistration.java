package infinituum.labellingcontainers.registration;

import infinituum.labellingcontainers.screens.LabelPrinterScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static infinituum.labellingcontainers.LabellingContainers.MODID;

public class ScreenRegistration {
    public static final ScreenHandlerType<LabelPrinterScreenHandler> LABEL_PRINTER_SCREEN_HANDLER = new ScreenHandlerType<>(LabelPrinterScreenHandler::new);

    public static void init() {
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(MODID, "label_printer_screen"), LABEL_PRINTER_SCREEN_HANDLER);
    }
}
