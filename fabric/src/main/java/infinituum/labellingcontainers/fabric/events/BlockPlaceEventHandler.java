package infinituum.labellingcontainers.fabric.events;

import infinituum.labellingcontainers.utils.InheritTagAction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BlockPlaceEventHandler {
    public static void handle(Player player, Level level, BlockState blockState, BlockPos blockPos) {
        if (level.isClientSide()) return;

        InheritTagAction.handle(player, level, blockPos, blockState);
    }
}
