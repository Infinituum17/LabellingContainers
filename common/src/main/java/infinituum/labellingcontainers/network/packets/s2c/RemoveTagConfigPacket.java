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

public final class RemoveTagConfigPacket implements CustomPacketPayload {
    public static final Type<RemoveTagConfigPacket> PACKET_TYPE = new Type<>(id("remove_tag_taggable_blocks_config_packet"));
    public static final StreamCodec<ByteBuf, RemoveTagConfigPacket> CODEC = StreamCodec.of(RemoveTagConfigPacket::encode, RemoveTagConfigPacket::new);
    private final String tag;

    public RemoveTagConfigPacket(ByteBuf byteBuf) {
        FriendlyByteBuf buf = new FriendlyByteBuf(byteBuf);

        this.tag = buf.readUtf();
    }

    public RemoveTagConfigPacket(String tag) {
        this.tag = tag;
    }

    private static void encode(ByteBuf byteBuf, RemoveTagConfigPacket packet) {
        FriendlyByteBuf buf = new FriendlyByteBuf(byteBuf);

        buf.writeUtf(packet.tag);
    }

    public static void register() {
        if (Platform.getEnvironment() == Env.CLIENT) {
            NetworkManager.registerReceiver(NetworkManager.s2c(), PACKET_TYPE, CODEC, RemoveTagConfigPacket::handler);
        } else {
            NetworkManager.registerS2CPayloadType(PACKET_TYPE, CODEC);
        }
    }

    @Environment(EnvType.CLIENT)
    private void handler(NetworkManager.PacketContext packetContext) {
        FastConfigs.get(CompatibleContainers.class).removeTag(tag);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }
}
