package infinituum.labellingcontainers;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PlatformHelper {
    /**
     * Locates the correct BlockEntity based on platform-specific interactions with mods.
     * @param world The World where we want to locate this BlockEntity.
     * @param blockPos The block position we are starting this operation from.
     * @return A BlockEntity or <code>null</code> if none is found.
     */
    @ExpectPlatform
    public static BlockEntity locateTargetBlockEntity(Level world, BlockPos blockPos, BlockState state) {
        throw new AssertionError();
    }
}