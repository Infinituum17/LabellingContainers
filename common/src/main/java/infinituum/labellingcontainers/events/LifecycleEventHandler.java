package infinituum.labellingcontainers.events;

import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;

import static infinituum.labellingcontainers.LabellingContainers.TAGGABLE_BLOCKS;

public class LifecycleEventHandler {
    public static void handle() {
        for(Block block : Registry.BLOCK) {
            if (block instanceof EntityBlock) {
                // TODO: add valid blocks / Check if important
                TAGGABLE_BLOCKS.put(block.toString(), block);
            }
        }
    }
}
