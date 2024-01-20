package infinituum.labellingcontainers;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.registry.menu.MenuRegistry;
import infinituum.labellingcontainers.guis.LabelPrinterGui;
import infinituum.labellingcontainers.huds.HudInfoDisplay;

import static infinituum.labellingcontainers.registration.ScreenRegistration.LABEL_PRINTER_SCREEN_HANDLER;

public class LabellingContainersClientSetup {
    public static void init() {
        ClientLifecycleEvent.CLIENT_SETUP.register((client) -> {
            ClientGuiEvent.RENDER_HUD.register(new HudInfoDisplay());
            MenuRegistry.registerScreenFactory(LABEL_PRINTER_SCREEN_HANDLER.get(), LabelPrinterGui::new);
        });
    }
}
