package infinituum.labellingcontainers.utils;

import infinituum.labellingcontainers.PlatformHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.jetbrains.annotations.NotNull;

public final class BlockEntityHelper {
    public static BlockEntity locateTargetBlockEntity(@NotNull Level world, BlockPos pos, BlockState state) {
        Block block = state.getBlock();

        if (block == Blocks.CHEST) {
            return getConnectedChest(world, pos, state);
        } else {
            return PlatformHelper.locateTargetBlockEntity(world, pos, state);
        }
    }

    public static BlockEntity getConnectedChest(Level level, BlockPos pos, BlockState state) {
        ChestType chestType = state.getValue(ChestBlock.TYPE);

        if (chestType == ChestType.SINGLE) return level.getBlockEntity(pos);

        Direction facingDirection = state.getValue(ChestBlock.FACING);
        BlockPos neighbourPosition = pos.relative(chestType == ChestType.LEFT ? facingDirection.getClockWise() : facingDirection.getCounterClockWise());

        if (level.getBlockEntity(neighbourPosition) != null && level.getBlockEntity(neighbourPosition) instanceof ChestBlockEntity blockEntity) {
            return blockEntity;
        }

        return level.getBlockEntity(pos);
    }
}
