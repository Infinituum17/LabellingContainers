package infinituum.labellingcontainers;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientTooltipEvent;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.registry.menu.MenuRegistry;
import infinituum.labellingcontainers.events.TooltipEventHandler;
import infinituum.labellingcontainers.guis.LabelPrinterGui;
import infinituum.labellingcontainers.huds.HudInfoDisplay;
import infinituum.labellingcontainers.huds.LabelPrinterHudInfoDisplay;
import infinituum.labellingcontainers.huds.utils.HUDPositions;
import infinituum.labellingcontainers.network.Packets;
import net.fabricmc.api.EnvType;

import java.nio.charset.Charset;

import static infinituum.labellingcontainers.LabellingContainers.LOGGER;
import static infinituum.labellingcontainers.LabellingContainersConfig.PLAYER_PREFERENCES_CONFIG;
import static infinituum.labellingcontainers.registration.ScreenRegistration.LABEL_PRINTER_SCREEN_HANDLER;

public class LabellingContainersClientSetup {
    public static void init() {
        if (Platform.getEnv() == EnvType.CLIENT) {
            ClientLifecycleEvent.CLIENT_SETUP.register((client) -> {
                ClientGuiEvent.RENDER_HUD.register(new HudInfoDisplay());
                ClientGuiEvent.RENDER_HUD.register(new LabelPrinterHudInfoDisplay());
                MenuRegistry.registerScreenFactory(LABEL_PRINTER_SCREEN_HANDLER.get(), LabelPrinterGui::new);

                LabellingContainersConfig.initClient();

                NetworkManager.registerReceiver(NetworkManager.s2c(), Packets.PLAYER_PREFERENCES_CONFIG_UPDATE, (buf, context) -> {
                    int length = buf.readInt();
                    String ordinalHud = buf.readCharSequence(length, Charset.defaultCharset()).toString();

                    PLAYER_PREFERENCES_CONFIG.get().setHudPosition(HUDPositions.fromReadable(ordinalHud));
                    PLAYER_PREFERENCES_CONFIG.writeCurrentToDisk();
                });
            });

            ClientTooltipEvent.ITEM.register(TooltipEventHandler::handle);
        } else {
            LOGGER.warn("Could not run Client Setup (mod is probably running on a Server-Only instance)");
        }
    }
}
