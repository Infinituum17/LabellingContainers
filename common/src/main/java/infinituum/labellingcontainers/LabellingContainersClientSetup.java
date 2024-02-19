package infinituum.labellingcontainers;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.menu.MenuRegistry;
import infinituum.labellingcontainers.guis.LabelPrinterGui;
import infinituum.labellingcontainers.huds.HudInfoDisplay;
import net.fabricmc.api.EnvType;

import static infinituum.labellingcontainers.LabellingContainers.LOGGER;
import static infinituum.labellingcontainers.registration.ScreenRegistration.LABEL_PRINTER_SCREEN_HANDLER;

public class LabellingContainersClientSetup {
    public static void init() {
        if (Platform.getEnv() == EnvType.CLIENT) {
            ClientLifecycleEvent.CLIENT_SETUP.register((client) -> {
                ClientGuiEvent.RENDER_HUD.register(new HudInfoDisplay());
                MenuRegistry.registerScreenFactory(LABEL_PRINTER_SCREEN_HANDLER.get(), LabelPrinterGui::new);
            });
        } else {
            LOGGER.warn("Could not run Client Setup (mod is probably running on a Server-Only instance)");
        }
    }
}
