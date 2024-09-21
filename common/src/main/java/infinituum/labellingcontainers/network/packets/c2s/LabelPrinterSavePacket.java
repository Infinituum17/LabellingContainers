package infinituum.labellingcontainers.network.packets.c2s;

import dev.architectury.networking.NetworkManager;
import infinituum.labellingcontainers.items.LabelPrinterItem;
import infinituum.labellingcontainers.registration.ItemRegistration;
import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static infinituum.labellingcontainers.utils.CommonHelper.id;

public record LabelPrinterSavePacket(String label) implements CustomPacketPayload {
    public static final Type<LabelPrinterSavePacket> PACKET_TYPE = new Type<>(id("label_printer_save_packet"));
    public static final StreamCodec<ByteBuf, LabelPrinterSavePacket> CODEC = StreamCodec.of(LabelPrinterSavePacket::encode, LabelPrinterSavePacket::new);

    public LabelPrinterSavePacket(ByteBuf byteBuf) {
        this(new FriendlyByteBuf(byteBuf).readUtf());
    }

    private static void encode(ByteBuf byteBuf, LabelPrinterSavePacket labelPrinterSavePacket) {
        FriendlyByteBuf buf = new FriendlyByteBuf(byteBuf);

        buf.writeUtf(labelPrinterSavePacket.label);
    }

    public static void register() {
        NetworkManager.registerReceiver(NetworkManager.c2s(), PACKET_TYPE, CODEC, LabelPrinterSavePacket::handler);
    }

    @Environment(EnvType.SERVER)
    private void handler(NetworkManager.PacketContext packetContext) {
        ItemStack itemInHand = packetContext.getPlayer().getMainHandItem();

        if (!itemInHand.isEmpty()) {
            if (itemInHand.is(ItemRegistration.LABEL_PRINTER.get())) {
                LabelPrinterItem.setLabel(itemInHand, label);
            }
        }
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }
}
