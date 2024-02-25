package infinituum.labellingcontainers.fabric;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlatformHelperImpl {
    public static BlockEntity locateTargetBlockEntity(World world, BlockPos blockPos) {
        return world.getBlockEntity(blockPos);
    }
}