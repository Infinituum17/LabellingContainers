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
    public static FastConfig MAIN_CONFIG = new FastConfig(Platform.getConfigFolder(), MOD_ID);
    public static FastConfig MOD_CONFIG = MAIN_CONFIG.getSubDirConfig();

    public static FastConfigFile<TaggableBlocks> TAGGABLE_BLOCKS_CONFIG;

    public static void commonInit() {
        TAGGABLE_BLOCKS_CONFIG = MOD_CONFIG.getConfigFile(TaggableBlocks.class);
    }

    @Environment(EnvType.CLIENT)
    public static FastConfigFile<PlayerPreferences> PLAYER_PREFERENCES_CONFIG;

    @Environment(EnvType.CLIENT)
    public static void initClient() {
        PLAYER_PREFERENCES_CONFIG = MOD_CONFIG.getConfigFile(PlayerPreferences.class);
    }
}
