package infinituum.labellingcontainers.neoforge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PlatformHelperImpl {
    public static BlockEntity locateTargetBlockEntity(Level world, BlockPos blockPos, BlockState state) {
        return world.getBlockEntity(blockPos);
    }
}
