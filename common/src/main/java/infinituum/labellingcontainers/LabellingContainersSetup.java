package infinituum.labellingcontainers;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientTooltipEvent;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.registry.menu.MenuRegistry;
import infinituum.fastconfigapi.FastConfigs;
import infinituum.labellingcontainers.config.CompatibleContainers;
import infinituum.labellingcontainers.config.PlayerPreferences;
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
import static infinituum.labellingcontainers.registration.ScreenRegistration.LABEL_PRINTER_SCREEN_HANDLER;

public final class LabellingContainersSetup {
    public static void initClient() {
        if (Platform.getEnv() == EnvType.CLIENT) {
            ClientLifecycleEvent.CLIENT_SETUP.register((client) -> {
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
        NetworkManager.registerReceiver(NetworkManager.c2s(), Packets.C2S_REQUEST_TAGGABLE_BLOCKS_CONFIG, (buf, context) -> {
            CompatibleContainers config = FastConfigs.get(CompatibleContainers.class);

            FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
            Set<String> ids = config.getIds();
            Set<String> tags = config.getTags();

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

    @Environment(EnvType.CLIENT)
    public static void clientRegisterNetworkReceivers() {
        NetworkManager.registerReceiver(NetworkManager.s2c(), Packets.S2C_PREFERENCES_CONFIG_UPDATE,
                (buf, context) -> FastConfigs.editAndSave(PlayerPreferences.class, config -> {
                    String ordinalHud = buf.readUtf();

                    config.setHudPosition(HudPositions.fromReadable(ordinalHud));
                }));

        NetworkManager.registerReceiver(NetworkManager.s2c(), Packets.S2C_SYNC_TAGGABLE_BLOCKS_CONFIG,
                (buf, context) -> FastConfigs.editAndSave(CompatibleContainers.class, config -> {
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

                    config.setIds(ids);
                    config.setTags(tags);
                }));

        NetworkManager.registerReceiver(NetworkManager.s2c(), Packets.S2C_ADD_ID_TAGGABLE_BLOCKS_CONFIG,
                (buf, context) -> FastConfigs.editAndSave(CompatibleContainers.class, config -> config.addId(buf.readUtf())));

        NetworkManager.registerReceiver(NetworkManager.s2c(), Packets.S2C_REMOVE_ID_TAGGABLE_BLOCKS_CONFIG,
                (buf, context) -> FastConfigs.editAndSave(CompatibleContainers.class, config -> config.removeId(buf.readUtf())));

        NetworkManager.registerReceiver(NetworkManager.s2c(), Packets.S2C_ADD_TAG_TAGGABLE_BLOCKS_CONFIG,
                (buf, context) -> FastConfigs.editAndSave(CompatibleContainers.class, config -> config.addTag(buf.readUtf())));

        NetworkManager.registerReceiver(NetworkManager.s2c(), Packets.S2C_REMOVE_TAG_TAGGABLE_BLOCKS_CONFIG,
                (buf, context) -> FastConfigs.editAndSave(CompatibleContainers.class, config -> config.removeTag(buf.readUtf())));
    }
}
