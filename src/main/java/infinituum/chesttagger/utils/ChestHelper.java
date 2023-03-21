package infinituum.chesttagger.utils;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ChestHelper {
    public static ChestBlockEntity getConnectedChestBlockEntity(World world, BlockPos pos, BlockState state) {
        if(world == null) return null;

        ChestType chestType = state.get(ChestBlock.CHEST_TYPE);

        if(chestType == ChestType.SINGLE) return null;

        Direction facingDirection = state.get(ChestBlock.FACING);
        BlockPos neighbourPosition = pos.offset(chestType == ChestType.LEFT ? facingDirection.rotateYClockwise() : facingDirection.rotateYCounterclockwise());

        if(world.getBlockEntity(neighbourPosition) != null && world.getBlockEntity(neighbourPosition) instanceof ChestBlockEntity blockEntity) {
            return blockEntity;
        }

        return null;
    }
}
