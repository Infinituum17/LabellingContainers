package infinituum.labellingcontainers.forge;

import dev.architectury.platform.Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.cyclops.colossalchests.block.ChestWall;
import org.cyclops.colossalchests.block.ColossalChest;

public class PlatformHelperImpl {
    public static BlockEntity locateTargetBlockEntity(Level world, BlockPos blockPos, BlockState state) {
        if(Platform.isModLoaded("colossalchests")) {
            BlockState blockState = world.getBlockState(blockPos);
            Block block = blockState.getBlock();

            if(block instanceof ChestWall && blockState.getValue(ColossalChest.ENABLED)) {
                BlockPos corePos = ColossalChest.getCoreLocation(((ChestWall) block).getMaterial(), world, blockPos);

                if(corePos != null) return world.getBlockEntity(corePos);
            }
        }

        return world.getBlockEntity(blockPos);
    }
}
