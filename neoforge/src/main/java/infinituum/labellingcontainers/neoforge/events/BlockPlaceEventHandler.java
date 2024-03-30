package infinituum.labellingcontainers.neoforge.events;

import infinituum.labellingcontainers.utils.InheritTagAction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.level.BlockEvent;

@Mod.EventBusSubscriber
public class BlockPlaceEventHandler {
    @SubscribeEvent
    public static void handle(BlockEvent.EntityPlaceEvent event) {
        if (event.getLevel().isClientSide()) return;

        if (event.getEntity() instanceof Player player) {
            InheritTagAction.handle(player, (Level) event.getLevel(), event.getPos(), event.getPlacedBlock());
        }
    }
}
