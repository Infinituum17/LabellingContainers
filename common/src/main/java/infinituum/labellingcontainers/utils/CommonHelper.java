package infinituum.labellingcontainers.utils;


import net.minecraft.resources.ResourceLocation;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;

public class CommonHelper {
    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
