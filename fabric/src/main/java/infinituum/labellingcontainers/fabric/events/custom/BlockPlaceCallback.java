package infinituum.labellingcontainers.fabric.events.custom;


import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Callback for placing a block.
 * Called AFTER the block is placed, BlockEntity is created, BlockState is set.
 * Upon return:
 * - SUCCESS cancels further processing and continues with normal block placement.
 * - PASS falls back to further processing and defaults to SUCCESS if no other listeners are available.
 * - FAIL falls back to further processing and defaults to SUCCESS if no other listeners are available.
 */
public interface BlockPlaceCallback {
    Event<BlockPlaceCallback> EVENT = EventFactory.createArrayBacked(BlockPlaceCallback.class, (listeners) -> (player, level, blockState, blockPos) -> {
        for (BlockPlaceCallback listener : listeners) {
            listener.interact(player, level, blockState, blockPos);
        }
    });

    void interact(Player player, Level level, BlockState blockState, BlockPos blockPos);
}