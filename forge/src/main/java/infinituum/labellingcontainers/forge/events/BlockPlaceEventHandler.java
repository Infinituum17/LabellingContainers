package infinituum.labellingcontainers.forge.events;

import infinituum.labellingcontainers.utils.BlockEntityHelper;
import infinituum.labellingcontainers.utils.Taggable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BlockPlaceEventHandler {
    @SubscribeEvent
    public static void handle(BlockEvent.EntityPlaceEvent event) {
        var entity = event.getEntity();
        var level = (Level) event.getLevel();
        var blockState = event.getPlacedBlock();
        var blockPos = event.getPos();

        if (!(entity instanceof Player) && level.isClientSide()) return;

        if (blockState.getBlock() instanceof EntityBlock) {
            BlockEntity currentBlockEntity = level.getBlockEntity(blockPos);
            BlockEntity connectedBlockEntity = BlockEntityHelper.locateTargetBlockEntity(level, blockPos, blockState);

            if (currentBlockEntity instanceof Taggable currentTaggable && connectedBlockEntity instanceof Taggable connectedTaggable) {
                currentTaggable.labellingcontainers$setLabel(connectedTaggable.labellingcontainers$getLabel(), false);
                currentTaggable.labellingcontainers$setDisplayItem(connectedTaggable.labellingcontainers$getDisplayItem(), false);
            }
        }

        return;
    }
}
