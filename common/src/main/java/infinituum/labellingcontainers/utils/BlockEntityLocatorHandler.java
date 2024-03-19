package infinituum.labellingcontainers.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface BlockEntityLocatorHandler {
    static BlockEntity handle(Level level, BlockPos blockPos, BlockState blockState) {
        return level.getBlockEntity(blockPos);
    }
}
