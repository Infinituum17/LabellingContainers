package infinituum.labellingcontainers.events;

import dev.architectury.event.EventResult;
import infinituum.labellingcontainers.utils.BlockEntityHelper;
import infinituum.labellingcontainers.utils.Taggable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEventPlaceHandler {
    public static EventResult handle(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        if (!(entity instanceof Player) && level.isClientSide()) return EventResult.pass();

        if (blockState.getBlock() instanceof EntityBlock) {
            BlockEntity currentBlockEntity = level.getBlockEntity(blockPos);
            BlockEntity connectedBlockEntity = BlockEntityHelper.locateTargetBlockEntity(level, blockPos, blockState);

            if (currentBlockEntity instanceof Taggable currentTaggable && connectedBlockEntity instanceof Taggable connectedTaggable) {
                currentTaggable.labellingcontainers$setLabel(connectedTaggable.labellingcontainers$getLabel(), false);
                currentTaggable.labellingcontainers$setDisplayItem(connectedTaggable.labellingcontainers$getDisplayItem(), false);
            }
        }

        return EventResult.pass();
    }
}
