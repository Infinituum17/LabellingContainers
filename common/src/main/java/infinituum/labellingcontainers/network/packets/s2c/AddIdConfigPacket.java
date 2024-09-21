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

public final class AddIdConfigPacket implements CustomPacketPayload {
    public static final Type<AddIdConfigPacket> PACKET_TYPE = new Type<>(id("add_id_taggable_blocks_config_packet"));
    public static final StreamCodec<ByteBuf, AddIdConfigPacket> CODEC = StreamCodec.of(AddIdConfigPacket::encode, AddIdConfigPacket::new);
    private final String id;

    public AddIdConfigPacket(ByteBuf byteBuf) {
        FriendlyByteBuf buf = new FriendlyByteBuf(byteBuf);

        this.id = buf.readUtf();
    }

    public AddIdConfigPacket(String id) {
        this.id = id;
    }

    private static void encode(ByteBuf byteBuf, AddIdConfigPacket packet) {
        FriendlyByteBuf buf = new FriendlyByteBuf(byteBuf);

        buf.writeUtf(packet.id);
    }

    public static void register() {
        if (Platform.getEnvironment() == Env.CLIENT) {
            NetworkManager.registerReceiver(NetworkManager.s2c(), PACKET_TYPE, CODEC, AddIdConfigPacket::handler);
        } else {
            NetworkManager.registerS2CPayloadType(PACKET_TYPE, CODEC);
        }
    }

    @Environment(EnvType.CLIENT)
    private void handler(NetworkManager.PacketContext packetContext) {
        FastConfigs.editAndSave(CompatibleContainers.class, config -> config.addId(id));
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }
}
