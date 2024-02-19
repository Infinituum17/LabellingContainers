package infinituum.labellingcontainers.registration;

import dev.architectury.networking.NetworkManager;
import infinituum.labellingcontainers.network.Packets;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class EventHandlersRegistration {
    public static void init() {
        NetworkManager.registerReceiver(NetworkManager.c2s(), Packets.LABEL_UPDATE_PACKET_ID, (buf, context) -> {
            String label = buf.readString();
            ItemStack item = context.getPlayer().getMainHandStack();

            if (!item.isEmpty()) {
                if (item.isOf(ItemRegistration.LABEL_PRINTER.get())) {
                    NbtCompound nbt = new NbtCompound();
                    nbt.putString("text", label);

                    item.setSubNbt("Label", nbt);
                }
            }
        });
    }
}