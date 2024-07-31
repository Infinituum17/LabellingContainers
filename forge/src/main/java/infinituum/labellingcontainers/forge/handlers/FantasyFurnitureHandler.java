package infinituum.labellingcontainers.forge.handlers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import xyz.apex.forge.apexcore.lib.block.IMultiBlock;

public final class FantasyFurnitureHandler {
    public static BlockEntity handle(Level level, BlockPos blockPos, BlockState blockState) {
        Block block = blockState.getBlock();

        if (block instanceof IMultiBlock multiBlock) {
            BlockPos origin = multiBlock.getMultiBlockOriginPos(blockState, blockPos);

            return level.getBlockEntity(origin);
        }

        return level.getBlockEntity(blockPos);
    }
}
