package infinituum.labellingcontainers.config;

import infinituum.fastconfigapi.api.annotations.FastConfig;
import infinituum.fastconfigapi.api.annotations.Loader;
import infinituum.fastconfigapi.api.serializers.JSONSerializer;
import infinituum.labellingcontainers.huds.utils.HudPositions;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;

@FastConfig(
        subdirectory = MOD_ID,
        side = FastConfig.Side.CLIENT,
        loader = @Loader(
                type = Loader.Type.URL,
                deserializer = JSONSerializer.class,
                target = "https://raw.githubusercontent.com/Infinituum17/LabellingContainers/1.20.1/defaults/configs/player-preferences.json"
        )
)
public final class PlayerPreferences {
    private String hudPosition = HudPositions.toReadable(HudPositions.getDefault());

    public HudPositions getHUDPosition() {
        return HudPositions.fromReadable(this.hudPosition);
    }

    public void setHudPosition(HudPositions position) {
        this.hudPosition = HudPositions.toReadable(position);
    }
}
