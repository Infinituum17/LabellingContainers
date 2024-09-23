package infinituum.labellingcontainers.neoforge.events;

import infinituum.labellingcontainers.guis.LabelPrinterScreen;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import static infinituum.labellingcontainers.registration.ScreenRegistration.LABEL_PRINTER_SCREEN;


public final class ScreenRegistration {
    @SubscribeEvent
    private void registerScreens(RegisterMenuScreensEvent event) {
        event.register(LABEL_PRINTER_SCREEN.get(), LabelPrinterScreen::new);
    }
}
