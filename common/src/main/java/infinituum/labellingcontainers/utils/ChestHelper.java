package infinituum.labellingcontainers.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

public class ChestHelper {
    public static ChestBlockEntity getConnectedChestBlockEntity(Level world, BlockPos pos, BlockState state) {
        if (world == null) return null;

        ChestType chestType = state.getValue(ChestBlock.TYPE);

        if (chestType == ChestType.SINGLE) return null;

        Direction facingDirection = state.getValue(ChestBlock.FACING);
        BlockPos neighbourPosition = pos.relative(chestType == ChestType.LEFT ? facingDirection.getClockWise() : facingDirection.getCounterClockWise());

        if (world.getBlockEntity(neighbourPosition) != null && world.getBlockEntity(neighbourPosition) instanceof ChestBlockEntity blockEntity) {
            return blockEntity;
        }

        return null;
    }
}
