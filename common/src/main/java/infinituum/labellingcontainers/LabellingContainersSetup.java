package infinituum.labellingcontainers;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientTooltipEvent;
import dev.architectury.event.events.common.CommandPerformEvent;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.registry.menu.MenuRegistry;
import infinituum.labellingcontainers.events.ReloadCommandHandler;
import infinituum.labellingcontainers.events.TooltipEventHandler;
import infinituum.labellingcontainers.guis.LabelPrinterGui;
import infinituum.labellingcontainers.huds.HudInfoDisplay;
import infinituum.labellingcontainers.huds.LabelPrinterHudInfoDisplay;
import infinituum.labellingcontainers.huds.utils.HudPositions;
import infinituum.labellingcontainers.items.LabelPrinterItem;
import infinituum.labellingcontainers.network.Packets;
import infinituum.labellingcontainers.registration.ItemRegistration;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

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

        CommandPerformEvent.EVENT.register(ReloadCommandHandler::handle);
    }

    public static void serverRegisterNetworkReceivers() {
        NetworkManager.registerReceiver(NetworkManager.c2s(), Packets.C2S_REQUEST_TAGGABLE_BLOCKS_CONFIG, (buf, context) -> {
            FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
            Set<String> ids = TAGGABLE_BLOCKS_CONFIG.getConfig().getIds();
            Set<String> tags = TAGGABLE_BLOCKS_CONFIG.getConfig().getTags();

            buffer.writeInt(ids.size());
            ids.forEach(buffer::writeUtf);

            buffer.writeInt(tags.size());
            tags.forEach(buffer::writeUtf);

            NetworkManager.sendToPlayer((ServerPlayer) context.getPlayer(), Packets.S2C_SYNC_TAGGABLE_BLOCKS_CONFIG, buffer);
        });

        NetworkManager.registerReceiver(NetworkManager.c2s(), Packets.C2S_LABEL_PRINTER_SAVE, (buf, context) -> {
            String label = buf.readUtf();
            ItemStack itemInHand = context.getPlayer().getMainHandItem();

            if (!itemInHand.isEmpty()) {
                if (itemInHand.is(ItemRegistration.LABEL_PRINTER.get())) {
                    LabelPrinterItem.setLabel(itemInHand, label);
                }
            }
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
                player -> NetworkManager.sendToServer(Packets.C2S_REQUEST_TAGGABLE_BLOCKS_CONFIG, new FriendlyByteBuf(Unpooled.buffer())));
    }

    // TODO: Test
    @Environment(EnvType.CLIENT)
    public static void clientRegisterNetworkReceivers() {
        NetworkManager.registerReceiver(NetworkManager.s2c(), Packets.S2C_PREFERENCES_CONFIG_UPDATE, (buf, context) -> {
            String ordinalHud = buf.readUtf();

            PLAYER_PREFERENCES_CONFIG.getConfig().setHudPosition(HudPositions.fromReadable(ordinalHud));
            PLAYER_PREFERENCES_CONFIG.saveCurrent();
        });

        NetworkManager.registerReceiver(NetworkManager.s2c(), Packets.S2C_SYNC_TAGGABLE_BLOCKS_CONFIG, (buf, context) -> {
            int idsSize = buf.readInt();
            Set<String> ids = new HashSet<>();

            for (int i = 0; i < idsSize; i++) {
                ids.add(buf.readUtf());
            }

            int tagsSize = buf.readInt();
            Set<String> tags = new HashSet<>();

            for (int i = 0; i < tagsSize; i++) {
                tags.add(buf.readUtf());
            }

            TAGGABLE_BLOCKS_CONFIG.getConfig().setIds(ids);
            TAGGABLE_BLOCKS_CONFIG.getConfig().setTags(tags);
        });

        NetworkManager.registerReceiver(NetworkManager.s2c(), Packets.S2C_ADD_ID_TAGGABLE_BLOCKS_CONFIG,
                (buf, context) -> TAGGABLE_BLOCKS_CONFIG.getConfig().addId(buf.readUtf()));

        NetworkManager.registerReceiver(NetworkManager.s2c(), Packets.S2C_REMOVE_ID_TAGGABLE_BLOCKS_CONFIG,
                (buf, context) -> TAGGABLE_BLOCKS_CONFIG.getConfig().removeId(buf.readUtf()));

        NetworkManager.registerReceiver(NetworkManager.s2c(), Packets.S2C_ADD_TAG_TAGGABLE_BLOCKS_CONFIG,
                (buf, context) -> TAGGABLE_BLOCKS_CONFIG.getConfig().addTag(buf.readUtf()));

        NetworkManager.registerReceiver(NetworkManager.s2c(), Packets.S2C_REMOVE_TAG_TAGGABLE_BLOCKS_CONFIG,
                (buf, context) -> TAGGABLE_BLOCKS_CONFIG.getConfig().removeTag(buf.readUtf()));
    }
}
