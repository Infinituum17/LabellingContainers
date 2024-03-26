package infinituum.labellingcontainers;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientTooltipEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.menu.MenuRegistry;
import infinituum.labellingcontainers.events.TooltipEventHandler;
import infinituum.labellingcontainers.guis.LabelPrinterGui;
import infinituum.labellingcontainers.huds.HudInfoDisplay;
import infinituum.labellingcontainers.huds.LabelPrinterHudInfoDisplay;
import net.fabricmc.api.EnvType;

import static infinituum.labellingcontainers.LabellingContainers.LOGGER;
import static infinituum.labellingcontainers.registration.ScreenRegistration.LABEL_PRINTER_SCREEN_HANDLER;

public class LabellingContainersClientSetup {
    public static void init() {
        if (Platform.getEnv() == EnvType.CLIENT) {
            ClientLifecycleEvent.CLIENT_SETUP.register((client) -> {
                ClientGuiEvent.RENDER_HUD.register(new HudInfoDisplay());
                ClientGuiEvent.RENDER_HUD.register(new LabelPrinterHudInfoDisplay());
                MenuRegistry.registerScreenFactory(LABEL_PRINTER_SCREEN_HANDLER.get(), LabelPrinterGui::new);
            });

            ClientTooltipEvent.ITEM.register(TooltipEventHandler::handle);
        } else {
            LOGGER.warn("Could not run Client Setup (mod is probably running on a Server-Only instance)");
        }
    }
}
