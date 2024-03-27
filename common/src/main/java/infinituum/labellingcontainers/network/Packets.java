package infinituum.labellingcontainers.network;

import net.minecraft.resources.ResourceLocation;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;

public class Packets {
    public static final ResourceLocation LABEL_UPDATE_PACKET_ID = new ResourceLocation(MOD_ID, "label_update_packet");
    public static final ResourceLocation PLAYER_PREFERENCES_CONFIG_UPDATE = new ResourceLocation(MOD_ID, "player_preferences_config_update");
}