package infinituum.labellingcontainers;

import dev.architectury.platform.Platform;
import infinituum.fastconfigapi.api.FastConfig;
import infinituum.fastconfigapi.api.FastConfigFile;
import infinituum.labellingcontainers.config.PlayerPreferences;
import infinituum.labellingcontainers.config.TaggableBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;

public class LabellingContainersConfig {
    public static FastConfig MAIN_CONFIG;
    public static FastConfig MOD_CONFIG;

    public static FastConfigFile<TaggableBlocks> TAGGABLE_BLOCKS_CONFIG;
    @Environment(EnvType.CLIENT)
    public static FastConfigFile<PlayerPreferences> PLAYER_PREFERENCES_CONFIG;

    public static void init() {
        MAIN_CONFIG = new FastConfig(Platform.getConfigFolder(), MOD_ID);
        MOD_CONFIG = MAIN_CONFIG.getSubDirConfig();

        TAGGABLE_BLOCKS_CONFIG = MOD_CONFIG.getConfigFile(TaggableBlocks.class);
    }

    @Environment(EnvType.CLIENT)
    public static void initClient() {
        PLAYER_PREFERENCES_CONFIG = MOD_CONFIG.getConfigFile(PlayerPreferences.class);
    }
}
