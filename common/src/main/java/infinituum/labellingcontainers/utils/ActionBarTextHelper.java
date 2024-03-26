package infinituum.labellingcontainers.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;

public class ActionBarTextHelper {
    public static void sendMessage(ServerPlayer player, Component message) {
        player.connection.send(new ClientboundSetActionBarTextPacket(message));
        player.connection.send(new ClientboundSetTitleTextPacket(Component.empty()));
    }
}
