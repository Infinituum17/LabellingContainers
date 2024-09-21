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

public final class AddTagConfigPacket implements CustomPacketPayload {
    public static final Type<AddTagConfigPacket> PACKET_TYPE = new Type<>(id("add_tag_taggable_blocks_config_packet"));
    public static final StreamCodec<ByteBuf, AddTagConfigPacket> CODEC = StreamCodec.of(AddTagConfigPacket::encode, AddTagConfigPacket::new);
    private final String tag;

    public AddTagConfigPacket(ByteBuf byteBuf) {
        FriendlyByteBuf buf = new FriendlyByteBuf(byteBuf);

        this.tag = buf.readUtf();
    }

    public AddTagConfigPacket(String tag) {
        this.tag = tag;
    }

    private static void encode(ByteBuf byteBuf, AddTagConfigPacket packet) {
        FriendlyByteBuf buf = new FriendlyByteBuf(byteBuf);

        buf.writeUtf(packet.tag);
    }

    public static void register() {
        if (Platform.getEnvironment() == Env.CLIENT) {
            NetworkManager.registerReceiver(NetworkManager.s2c(), PACKET_TYPE, CODEC, AddTagConfigPacket::handler);
        } else {
            NetworkManager.registerS2CPayloadType(PACKET_TYPE, CODEC);
        }
    }

    @Environment(EnvType.CLIENT)
    private void handler(NetworkManager.PacketContext packetContext) {
        FastConfigs.editAndSave(CompatibleContainers.class, config -> config.addTag(tag));
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }
}