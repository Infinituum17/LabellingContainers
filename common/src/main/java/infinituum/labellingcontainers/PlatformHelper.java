package infinituum.labellingcontainers;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlatformHelper {
    /**
     * Locates the correct BlockEntity based on platform-specific interactions with mods.
     * @param world The World where we want to locate this BlockEntity.
     * @param blockPos The block position we are starting this operation from.
     * @return A BlockEntity or <code>null</code> if none is found.
     */
    @ExpectPlatform
    public static BlockEntity locateTargetBlockEntity(World world, BlockPos blockPos) {
        throw new AssertionError();
    }
}
