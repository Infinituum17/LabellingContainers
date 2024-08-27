package infinituum.labellingcontainers.forge.events;

import infinituum.labellingcontainers.utils.InheritTagAction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public final class BlockPlaceEventHandler {
    @SubscribeEvent
    public static void handle(BlockEvent.EntityPlaceEvent event) {
        if (event.getLevel().isClientSide()) {
            return;
        }

        if (event.getEntity() instanceof Player player) {
            InheritTagAction.handle(player, (Level) event.getLevel(), event.getPos(), event.getPlacedBlock());
        }
    }
}