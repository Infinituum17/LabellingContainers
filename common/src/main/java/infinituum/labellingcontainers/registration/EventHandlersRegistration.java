package infinituum.labellingcontainers.registration;

import dev.architectury.networking.NetworkManager;
import infinituum.labellingcontainers.items.LabelPrinterItem;
import infinituum.labellingcontainers.network.Packets;
import net.minecraft.world.item.ItemStack;

public class EventHandlersRegistration {
    public static void init() {
        NetworkManager.registerReceiver(NetworkManager.c2s(), Packets.LABEL_UPDATE_PACKET_ID, (buf, context) -> {
            String label = buf.readUtf();
            ItemStack itemInHand = context.getPlayer().getMainHandItem();

            if (!itemInHand.isEmpty()) {
                if (itemInHand.is(ItemRegistration.LABEL_PRINTER.get())) {
                    LabelPrinterItem.setLabel(itemInHand, label);
                }
            }
        });
    }
}