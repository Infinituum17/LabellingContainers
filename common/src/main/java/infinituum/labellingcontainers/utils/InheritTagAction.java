package infinituum.labellingcontainers.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class InheritTagAction {
    public static void handle(Player player, Level level, BlockPos blockPos, BlockState blockState) {
        if (player == null || level.isClientSide()) return;

        if (blockState.getBlock() instanceof EntityBlock) {
            BlockEntity currentBlockEntity = level.getBlockEntity(blockPos);
            BlockEntity connectedBlockEntity = BlockEntityHelper.locateTargetBlockEntity(level, blockPos, blockState);

            if (currentBlockEntity instanceof Taggable currentTaggable && connectedBlockEntity instanceof Taggable connectedTaggable) {
                currentTaggable.labellingcontainers$setLabel(connectedTaggable.labellingcontainers$getLabel(), false);
                currentTaggable.labellingcontainers$setDisplayItem(connectedTaggable.labellingcontainers$getDisplayItem(), false);
            }
        }
    }
}
