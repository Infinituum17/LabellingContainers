package infinituum.labellingcontainers.registration;

import infinituum.labellingcontainers.screens.LabelPrinterScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import static infinituum.labellingcontainers.LabellingContainers.MODID;

public class ScreenRegistration {
    public static final ScreenHandlerType<LabelPrinterScreenHandler> LABEL_PRINTER_SCREEN_HANDLER = new ScreenHandlerType<>(LabelPrinterScreenHandler::new, FeatureSet.empty());

    public static void init() {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(MODID, "label_printer_screen"), LABEL_PRINTER_SCREEN_HANDLER);
    }
}
