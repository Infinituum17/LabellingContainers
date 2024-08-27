package infinituum.labellingcontainers.utils;

import infinituum.labellingcontainers.PlatformHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class BlockEntityHelper {
    public static BlockEntity locateTargetBlockEntity(@NotNull Level level, BlockPos pos, BlockState state) {
        Block block = state.getBlock();

        if (block instanceof AbstractChestBlock<?>) {
            return getConnectedChest(level, pos, state);
        }

        return PlatformHelper.locateTargetBlockEntity(level, pos, state);
    }

    public static BlockEntity getConnectedChest(Level level, BlockPos pos, BlockState state) {
        Optional<ChestType> optType = state.getOptionalValue(ChestBlock.TYPE);

        if (optType.isEmpty()) {
            return level.getBlockEntity(pos);
        }

        ChestType chestType = optType.get();

        if (chestType == ChestType.SINGLE) {
            return level.getBlockEntity(pos);
        }

        Direction facingDirection = state.getValue(ChestBlock.FACING);
        BlockPos neighbourPosition = pos.relative(chestType == ChestType.LEFT ? facingDirection.getClockWise() : facingDirection.getCounterClockWise());

        if (level.getBlockEntity(neighbourPosition) != null && level.getBlockEntity(neighbourPosition) instanceof ChestBlockEntity blockEntity) {
            return blockEntity;
        }

        return level.getBlockEntity(pos);
    }

    public interface BlockEntityLocatorHandler {
        static BlockEntity handle(Level level, BlockPos blockPos, BlockState blockState) {
            return level.getBlockEntity(blockPos);
        }
    }
}