package infinituum.labellingcontainers.fabric;

import infinituum.labellingcontainers.LCExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class LCExpectPlatformImpl {
    /**
     * This is our actual method to {@link LCExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
