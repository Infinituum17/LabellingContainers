package infinituum.labellingcontainers.config;

import infinituum.labellingcontainers.huds.utils.HudPositions;

public final class PlayerPreferences {
    private String hudPosition = HudPositions.toReadable(HudPositions.getDefault());

    public HudPositions getHUDPosition() {
        return HudPositions.fromReadable(this.hudPosition);
    }

    public void setHudPosition(HudPositions position) {
        this.hudPosition = HudPositions.toReadable(position);
    }
}
