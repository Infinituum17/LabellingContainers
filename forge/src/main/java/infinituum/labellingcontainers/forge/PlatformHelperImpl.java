package infinituum.labellingcontainers.forge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class PlatformHelperImpl {
    public static BlockEntity locateTargetBlockEntity(Level world, BlockPos blockPos, BlockState state) {
        if(Platform.isModLoaded("colossalchests")) {
            BlockState blockState = world.getBlockState(blockPos);
            Block block = blockState.getBlock();

            if (block instanceof ChestWall && blockState.getValue(ColossalChest.ENABLED)) {
                BlockPos corePos = ColossalChest.getCoreLocation(((ChestWall) block).getMaterial(), world, blockPos);

                if(corePos != null) return world.getBlockEntity(corePos);
            }
        }

        return world.getBlockEntity(blockPos);
    }
}