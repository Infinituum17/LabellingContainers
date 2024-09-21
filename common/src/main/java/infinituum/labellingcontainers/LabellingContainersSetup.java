package infinituum.labellingcontainers;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientTooltipEvent;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.registry.menu.MenuRegistry;
import infinituum.labellingcontainers.events.TooltipEventHandler;
import infinituum.labellingcontainers.guis.LabelPrinterGui;
import infinituum.labellingcontainers.huds.HudInfoDisplay;
import infinituum.labellingcontainers.huds.LabelPrinterHudInfoDisplay;
import infinituum.labellingcontainers.network.packets.c2s.RequestTaggableBlocksConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import static infinituum.labellingcontainers.LabellingContainers.LOGGER;
import static infinituum.labellingcontainers.registration.ScreenRegistration.LABEL_PRINTER_SCREEN_HANDLER;

public final class LabellingContainersSetup {
    public static void initClient() {
        if (Platform.getEnv() == EnvType.CLIENT) {
            ClientLifecycleEvent.CLIENT_SETUP.register((client) -> {
                clientRegisterScreens();
                clientRegisterEvents();
            });
        } else {
            LOGGER.warn("Could not run Client Setup (mod is probably running on a Server-Only instance)");
        }
    }

    @Environment(EnvType.CLIENT)
    public static void clientRegisterScreens() {
        MenuRegistry.registerScreenFactory(LABEL_PRINTER_SCREEN_HANDLER.get(), LabelPrinterGui::new);
    }

    @Environment(EnvType.CLIENT)
    public static void clientRegisterEvents() {
        ClientTooltipEvent.ITEM.register(TooltipEventHandler::handle);

        ClientGuiEvent.RENDER_HUD.register(new HudInfoDisplay());
        ClientGuiEvent.RENDER_HUD.register(new LabelPrinterHudInfoDisplay());

        ClientPlayerEvent.CLIENT_PLAYER_JOIN.register(player -> {
            NetworkManager.sendToServer(new RequestTaggableBlocksConfig());
        });
    }
}