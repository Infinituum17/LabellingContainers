package infinituum.labellingcontainers.forge;

import infinituum.labellingcontainers.LCExpectPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class LCExpectPlatformImpl {
    /**
     * This is our actual method to {@link LCExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
