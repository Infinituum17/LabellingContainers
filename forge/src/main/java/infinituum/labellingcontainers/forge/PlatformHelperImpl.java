package infinituum.labellingcontainers.forge;

import dev.architectury.platform.Platform;
import infinituum.labellingcontainers.forge.handlers.ColossalChestsHandler;
import infinituum.labellingcontainers.forge.handlers.FantasyFurnitureHandler;
import infinituum.labellingcontainers.forge.registration.resources.PlatformProvider;
import infinituum.labellingcontainers.utils.ContainerResource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class PlatformHelperImpl {
    public static BlockEntity locateTargetBlockEntity(Level level, BlockPos blockPos, BlockState blockState) {
        if (Platform.isModLoaded("colossalchests")) {
            return ColossalChestsHandler.handle(level, blockPos, blockState);
        }

        if (Platform.isModLoaded("fantasyfurniture")) {
            return FantasyFurnitureHandler.handle(level, blockPos, blockState);
        }

        return level.getBlockEntity(blockPos);
    }

    public static ContainerResource getPlatformSpecificProvider() {
        return new PlatformProvider();
    }
}