package infinituum.labellingcontainers;

import dev.architectury.injectables.annotations.ExpectPlatform;
import infinituum.labellingcontainers.utils.ContainerResource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class PlatformHelper {
    /**
     * Locates the correct {@link BlockEntity} based on platform-specific interactions with mods.
     *
     * @param level    The {@link Level} where we want to locate this {@link BlockEntity}.
     * @param blockPos The block position we are starting this operation from.
     * @return A {@link BlockEntity} or <code>null</code> if none is found.
     */
    @ExpectPlatform
    public static BlockEntity locateTargetBlockEntity(Level level, BlockPos blockPos, BlockState state) {
        throw new AssertionError();
    }

    /**
     * Gets the Platform specific Container Resource Provider.
     *
     * @return {@link ContainerResource}
     */
    @ExpectPlatform
    public static ContainerResource getPlatformSpecificProvider() {
        throw new AssertionError();
    }
}