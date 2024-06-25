package infinituum.labellingcontainers.forge.events;

import infinituum.labellingcontainers.utils.InheritTagAction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BlockPlaceEventHandler {
    @SubscribeEvent
    public static void handle(BlockEvent.EntityPlaceEvent event) {
        if (event.getWorld().isClientSide()) return;

        if (event.getEntity() instanceof Player player) {
            InheritTagAction.handle(player, (Level) event.getWorld(), event.getPos(), event.getPlacedBlock());
        }
    }
}
