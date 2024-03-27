package infinituum.labellingcontainers.config;

import infinituum.labellingcontainers.huds.utils.HUDPositions;

public class PlayerPreferences {
    private String hudPosition = HUDPositions.toReadable(HUDPositions.getDefault());

    public PlayerPreferences() {
    }

    public PlayerPreferences(int hudPosition) {
    }

    public HUDPositions getHUDPosition() {
        return HUDPositions.fromReadable(this.hudPosition);
    }

    public void setHudPosition(HUDPositions position) {
        this.hudPosition = HUDPositions.toReadable(position);
    }
}
