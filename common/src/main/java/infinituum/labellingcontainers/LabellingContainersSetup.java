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
import infinituum.labellingcontainers.huds.utils.HudPositions;
import infinituum.labellingcontainers.network.Packets;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashSet;
import java.util.Set;

import static infinituum.labellingcontainers.LabellingContainers.LOGGER;
import static infinituum.labellingcontainers.LabellingContainersConfig.PLAYER_PREFERENCES_CONFIG;
import static infinituum.labellingcontainers.LabellingContainersConfig.TAGGABLE_BLOCKS_CONFIG;
import static infinituum.labellingcontainers.registration.ScreenRegistration.LABEL_PRINTER_SCREEN_HANDLER;

public class LabellingContainersSetup {
    public static void initClient() {
        if (Platform.getEnv() == EnvType.CLIENT) {
            ClientLifecycleEvent.CLIENT_SETUP.register((client) -> {
                LabellingContainersConfig.initClient();

                clientRegisterScreens();
                clientRegisterEvents();
                clientRegisterNetworkReceivers();
            });
        } else {
            LOGGER.warn("Could not run Client Setup (mod is probably running on a Server-Only instance)");
        }
    }

    public static void initServer() {

        serverRegisterNetworkReceivers();
    }

    public static void serverRegisterNetworkReceivers() {
        NetworkManager.registerReceiver(NetworkManager.c2s(), Packets.REQUEST_TAGGABLE_BLOCKS_CONFIG, (buf, context) -> {
            FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
            Set<String> ids = TAGGABLE_BLOCKS_CONFIG.get().getIds();

            buffer.writeInt(ids.size());

            ids.forEach(buffer::writeUtf);

            NetworkManager.sendToPlayer((ServerPlayer) context.getPlayer(), Packets.SYNC_ALL_TAGGABLE_BLOCKS_CONFIG, buffer);
        });
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

        ClientPlayerEvent.CLIENT_PLAYER_JOIN.register(
                player -> NetworkManager.sendToServer(Packets.REQUEST_TAGGABLE_BLOCKS_CONFIG, new FriendlyByteBuf(Unpooled.buffer())));
    }

    @Environment(EnvType.CLIENT)
    public static void clientRegisterNetworkReceivers() {
        NetworkManager.registerReceiver(NetworkManager.s2c(), Packets.SEND_PLAYER_PREFERENCES_CONFIG_UPDATE, (buf, context) -> {
            String ordinalHud = buf.readUtf();

            PLAYER_PREFERENCES_CONFIG.get().setHudPosition(HudPositions.fromReadable(ordinalHud));
            PLAYER_PREFERENCES_CONFIG.writeCurrentToDisk();
        });

        NetworkManager.registerReceiver(NetworkManager.s2c(), Packets.SYNC_ALL_TAGGABLE_BLOCKS_CONFIG, (buf, context) -> {
            int setSize = buf.readInt();
            Set<String> set = new HashSet<>();

            for (int i = 0; i < setSize; i++) {
                set.add(buf.readUtf());
            }

            TAGGABLE_BLOCKS_CONFIG.get().setIds(set);
        });

        NetworkManager.registerReceiver(NetworkManager.s2c(), Packets.ADD_ONE_TAGGABLE_BLOCKS_CONFIG,
                (buf, context) -> TAGGABLE_BLOCKS_CONFIG.get().addId(buf.readUtf()));

        NetworkManager.registerReceiver(NetworkManager.s2c(), Packets.REMOVE_ONE_TAGGABLE_BLOCKS_CONFIG,
                (buf, context) -> TAGGABLE_BLOCKS_CONFIG.get().removeId(buf.readUtf()));
    }
}
