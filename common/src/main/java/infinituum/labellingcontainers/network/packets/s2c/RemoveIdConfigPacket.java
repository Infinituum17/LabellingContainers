package infinituum.labellingcontainers.network.packets.s2c;

import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import infinituum.fastconfigapi.FastConfigs;
import infinituum.labellingcontainers.config.CompatibleContainers;
import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import static infinituum.labellingcontainers.utils.CommonHelper.id;

public final class RemoveIdConfigPacket implements CustomPacketPayload {
    public static final Type<RemoveIdConfigPacket> PACKET_TYPE = new Type<>(id("remove_id_taggable_blocks_config_packet"));
    public static final StreamCodec<ByteBuf, RemoveIdConfigPacket> CODEC = StreamCodec.of(RemoveIdConfigPacket::encode, RemoveIdConfigPacket::new);
    private final String id;

    public RemoveIdConfigPacket(ByteBuf byteBuf) {
        FriendlyByteBuf buf = new FriendlyByteBuf(byteBuf);

        this.id = buf.readUtf();
    }

    public RemoveIdConfigPacket(String id) {
        this.id = id;
    }

    private static void encode(ByteBuf byteBuf, RemoveIdConfigPacket packet) {
        FriendlyByteBuf buf = new FriendlyByteBuf(byteBuf);

        buf.writeUtf(packet.id);
    }

    public static void register() {
        if (Platform.getEnvironment() == Env.CLIENT) {
            NetworkManager.registerReceiver(NetworkManager.s2c(), PACKET_TYPE, CODEC, RemoveIdConfigPacket::handler);
        } else {
            NetworkManager.registerS2CPayloadType(PACKET_TYPE, CODEC);
        }
    }

    @Environment(EnvType.CLIENT)
    private void handler(NetworkManager.PacketContext packetContext) {
        FastConfigs.get(CompatibleContainers.class).removeId(id);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }
}

