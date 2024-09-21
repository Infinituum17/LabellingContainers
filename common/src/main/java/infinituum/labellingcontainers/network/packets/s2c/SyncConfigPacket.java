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

import java.util.HashSet;
import java.util.Set;

import static infinituum.labellingcontainers.utils.CommonHelper.id;

public final class SyncConfigPacket implements CustomPacketPayload {
    public static final StreamCodec<ByteBuf, SyncConfigPacket> CODEC = StreamCodec.of(SyncConfigPacket::encode, SyncConfigPacket::new);
    public static final Type<SyncConfigPacket> PACKET_TYPE = new Type<>(id("sync_taggable_blocks_config_packet"));
    private final Set<String> ids;
    private final Set<String> tags;

    public SyncConfigPacket(ByteBuf byteBuf) {
        FriendlyByteBuf buf = new FriendlyByteBuf(byteBuf);
        this.ids = new HashSet<>();
        this.tags = new HashSet<>();

        int idsSize = buf.readInt();

        for (int i = 0; i < idsSize; i++) {
            ids.add(buf.readUtf());
        }

        int tagsSize = buf.readInt();

        for (int i = 0; i < tagsSize; i++) {
            tags.add(buf.readUtf());
        }
    }

    public SyncConfigPacket(Set<String> ids, Set<String> tags) {
        this.ids = ids;
        this.tags = tags;
    }

    private static void encode(ByteBuf byteBuf, SyncConfigPacket packet) {
        FriendlyByteBuf buf = new FriendlyByteBuf(byteBuf);

        buf.writeInt(packet.ids.size());
        packet.ids.forEach(buf::writeUtf);

        buf.writeInt(packet.tags.size());
        packet.tags.forEach(buf::writeUtf);
    }

    public static void register() {
        if (Platform.getEnvironment() == Env.CLIENT) {
            NetworkManager.registerReceiver(NetworkManager.s2c(), PACKET_TYPE, CODEC, SyncConfigPacket::handler);
        } else {
            NetworkManager.registerS2CPayloadType(PACKET_TYPE, CODEC);
        }
    }

    @Environment(EnvType.CLIENT)
    private void handler(NetworkManager.PacketContext packetContext) {
        FastConfigs.editAndSave(CompatibleContainers.class, config -> {
            config.setIds(ids);
            config.setTags(tags);
        });
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }
}
