package infinituum.labellingcontainers;

import dev.architectury.platform.Platform;
import infinituum.fastconfigapi.api.FastConfig;
import infinituum.fastconfigapi.api.FastConfigFile;
import infinituum.labellingcontainers.config.TaggableBlocksConfig;

public class LabellingContainersConfig {
    public static FastConfig MAIN_CONFIG;
    public static FastConfig MOD_CONFIG;

    public static FastConfigFile<TaggableBlocksConfig> TAGGABLE_BLOCKS_CONFIG;

    public static void init() {
        MAIN_CONFIG = new FastConfig(Platform.getConfigFolder());
        MOD_CONFIG = MAIN_CONFIG.getSubDirConfig();

        TAGGABLE_BLOCKS_CONFIG = MAIN_CONFIG.getConfigFile(TaggableBlocksConfig.class);
    }
}
