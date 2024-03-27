package infinituum.labellingcontainers.network;

import net.minecraft.resources.ResourceLocation;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;

public class Packets {
    public static final ResourceLocation LABEL_UPDATE_PACKET_ID = new ResourceLocation(MOD_ID, "label_update_packet");
    public static final ResourceLocation SEND_PLAYER_PREFERENCES_CONFIG_UPDATE = new ResourceLocation(MOD_ID, "send_player_preferences_config_update");
    public static final ResourceLocation REQUEST_TAGGABLE_BLOCKS_CONFIG = new ResourceLocation(MOD_ID, "request_taggable_blocks_config");
    public static final ResourceLocation SYNC_ALL_TAGGABLE_BLOCKS_CONFIG = new ResourceLocation(MOD_ID, "sync_taggable_blocks_config");
    public static final ResourceLocation ADD_ONE_TAGGABLE_BLOCKS_CONFIG = new ResourceLocation(MOD_ID, "add_one_taggable_blocks_config");
    public static final ResourceLocation REMOVE_ONE_TAGGABLE_BLOCKS_CONFIG = new ResourceLocation(MOD_ID, "remove_one_taggable_blocks_config");
}