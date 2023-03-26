package infinituum.chesttagger.registration;

import infinituum.chesttagger.network.Packets;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class EventHandlersRegistration {
    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(Packets.LABEL_UPDATE_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            String label = buf.readString();
            ItemStack item = player.getMainHandStack();

            if(!item.isEmpty()) {
                if(item.isOf(ItemRegistration.LABELLING_MACHINE)) {
                    NbtCompound nbt = new NbtCompound();
                    nbt.putString("text", label);

                    item.setSubNbt("Label", nbt);
                }
            }
        });
    }
}
