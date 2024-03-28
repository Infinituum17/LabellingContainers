package infinituum.labellingcontainers.forge.handlers;

import infinituum.labellingcontainers.utils.BlockEntityLocatorHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.cyclops.colossalchests.block.ChestWall;
import org.cyclops.colossalchests.block.ColossalChest;
import org.cyclops.colossalchests.block.Interface;

public class ColossalChestsHandler implements BlockEntityLocatorHandler {
    public static BlockEntity handle(Level level, BlockPos blockPos, BlockState blockState) {
        Block block = blockState.getBlock();
        if (block instanceof ChestWall wall && blockState.getValue(ColossalChest.ENABLED)) {
            BlockPos corePos = ColossalChest.getCoreLocation(wall.getMaterial(), level, blockPos);

            if (corePos != null) return level.getBlockEntity(corePos);
        } else if (block instanceof Interface wall && blockState.getValue(ColossalChest.ENABLED)) {
            BlockPos corePos = ColossalChest.getCoreLocation(wall.getMaterial(), level, blockPos);

            if (corePos != null) return level.getBlockEntity(corePos);
        }

        return level.getBlockEntity(blockPos);
    }
}