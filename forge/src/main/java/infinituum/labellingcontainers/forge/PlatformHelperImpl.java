package infinituum.labellingcontainers.forge;

import dev.architectury.platform.Platform;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.cyclops.colossalchests.block.ChestWall;
import org.cyclops.colossalchests.block.ColossalChest;

public class PlatformHelperImpl {
    public static BlockEntity locateTargetBlockEntity(World world, BlockPos blockPos) {
        if(Platform.isModLoaded("colossalchests")) {
            BlockState blockState = world.getBlockState(blockPos);
            Block block = blockState.getBlock();

            if(block instanceof ChestWall && blockState.get(ColossalChest.ENABLED)) {
                BlockPos corePos = ColossalChest.getCoreLocation(((ChestWall) block).getMaterial(), world, blockPos);

                if(corePos != null) return world.getBlockEntity(corePos);
            }
        }

        return world.getBlockEntity(blockPos);
    }
}