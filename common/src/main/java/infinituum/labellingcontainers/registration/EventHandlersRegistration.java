package infinituum.labellingcontainers.registration;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.networking.NetworkManager;
import infinituum.labellingcontainers.events.LifecycleEventHandler;
import infinituum.labellingcontainers.network.Packets;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class EventHandlersRegistration {
    public static void init() {
        NetworkManager.registerReceiver(NetworkManager.c2s(), Packets.LABEL_UPDATE_PACKET_ID, (buf, context) -> {
            String label = buf.readUtf();
            ItemStack item = context.getPlayer().getMainHandItem();

            if (!item.isEmpty()) {
                if (item.is(ItemRegistration.LABEL_PRINTER.get())) {
                    CompoundTag nbt = new CompoundTag();
                    nbt.putString("text", label);

                    item.addTagElement("Label", nbt);
                }
            }
        });

        LifecycleEvent.SETUP.register(LifecycleEventHandler::handle);
        // BlockEvent.PLACE.register(BlockEventPlaceHandler::handle);
    }
}