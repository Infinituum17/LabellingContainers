package infinituum.labellingcontainers.network.packets.s2c;

import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import infinituum.fastconfigapi.FastConfigs;
import infinituum.labellingcontainers.config.PlayerPreferences;
import infinituum.labellingcontainers.huds.utils.HudPositions;
import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import static infinituum.labellingcontainers.utils.CommonHelper.id;

public record UpdatePreferencesConfigPacket(String position) implements CustomPacketPayload {
    public static final StreamCodec<ByteBuf, UpdatePreferencesConfigPacket> CODEC = StreamCodec.of(UpdatePreferencesConfigPacket::encode, UpdatePreferencesConfigPacket::new);
    public static final Type<UpdatePreferencesConfigPacket> PACKET_TYPE = new Type<>(id("preferences_config_update_packet"));

    private UpdatePreferencesConfigPacket(ByteBuf byteBuf) {
        this(new FriendlyByteBuf(byteBuf).readUtf());
    }

    public static void register() {
        if (Platform.getEnvironment() == Env.CLIENT) {
            NetworkManager.registerReceiver(NetworkManager.s2c(), PACKET_TYPE, CODEC, UpdatePreferencesConfigPacket::handler);
        } else {
            NetworkManager.registerS2CPayloadType(PACKET_TYPE, CODEC);
        }
    }

    public static void encode(ByteBuf byteBuf, UpdatePreferencesConfigPacket packet) {
        FriendlyByteBuf buf = new FriendlyByteBuf(byteBuf);

        buf.writeUtf(packet.position());
    }

    @Environment(EnvType.CLIENT)
    private void handler(NetworkManager.PacketContext packetContext) {
        FastConfigs.editAndSave(
                PlayerPreferences.class,
                config -> config.setHudPosition(HudPositions.fromReadable(position))
        );
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }
}
