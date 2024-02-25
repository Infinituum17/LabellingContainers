package infinituum.labellingcontainers.fabric;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class PlatformHelperImpl {
    public static BlockEntity locateTargetBlockEntity(Level world, BlockPos blockPos) {
        return world.getBlockEntity(blockPos);
    }
}
