package infinituum.labellingcontainers.network;

import net.minecraft.resources.ResourceLocation;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;

public final class Packets {
    // C2S
    public static final ResourceLocation C2S_LABEL_PRINTER_SAVE = new ResourceLocation(MOD_ID, "label_printer_save_packet");
    public static final ResourceLocation C2S_REQUEST_TAGGABLE_BLOCKS_CONFIG = new ResourceLocation(MOD_ID, "request_taggable_blocks_config_packet");

    // S2C
    public static final ResourceLocation S2C_PREFERENCES_CONFIG_UPDATE = new ResourceLocation(MOD_ID, "preferences_config_update_packet");
    public static final ResourceLocation S2C_SYNC_TAGGABLE_BLOCKS_CONFIG = new ResourceLocation(MOD_ID, "sync_taggable_blocks_config_packet");

    public static final ResourceLocation S2C_ADD_ID_TAGGABLE_BLOCKS_CONFIG = new ResourceLocation(MOD_ID, "add_id_taggable_blocks_config_packet");
    public static final ResourceLocation S2C_REMOVE_ID_TAGGABLE_BLOCKS_CONFIG = new ResourceLocation(MOD_ID, "remove_id_taggable_blocks_config_packet");

    public static final ResourceLocation S2C_ADD_TAG_TAGGABLE_BLOCKS_CONFIG = new ResourceLocation(MOD_ID, "add_tag_taggable_blocks_config_packet");
    public static final ResourceLocation S2C_REMOVE_TAG_TAGGABLE_BLOCKS_CONFIG = new ResourceLocation(MOD_ID, "remove_tag_taggable_blocks_config_packet");
}