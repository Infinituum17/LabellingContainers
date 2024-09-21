package infinituum.labellingcontainers.network.packets.c2s;

import dev.architectury.networking.NetworkManager;
import infinituum.fastconfigapi.FastConfigs;
import infinituum.labellingcontainers.config.CompatibleContainers;
import infinituum.labellingcontainers.network.packets.s2c.SyncConfigPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import static infinituum.labellingcontainers.utils.CommonHelper.id;

public record RequestTaggableBlocksConfig() implements CustomPacketPayload {
    public static final Type<RequestTaggableBlocksConfig> PACKET_TYPE = new Type<>(id("request_taggable_blocks_config_packet"));
    public static final StreamCodec<ByteBuf, RequestTaggableBlocksConfig> CODEC = StreamCodec.of(RequestTaggableBlocksConfig::encode, RequestTaggableBlocksConfig::new);

    public RequestTaggableBlocksConfig(ByteBuf byteBuf) {
        this();
    }

    private static void encode(ByteBuf byteBuf, RequestTaggableBlocksConfig labelPrinterSavePacket) {
    }

    public static void register() {
        NetworkManager.registerReceiver(NetworkManager.c2s(), PACKET_TYPE, CODEC, RequestTaggableBlocksConfig::handler);
    }

    private void handler(NetworkManager.PacketContext packetContext) {
        CompatibleContainers config = FastConfigs.get(CompatibleContainers.class);
        NetworkManager.sendToPlayer((ServerPlayer) packetContext.getPlayer(), new SyncConfigPacket(config.getIds(), config.getTags()));
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }
}
