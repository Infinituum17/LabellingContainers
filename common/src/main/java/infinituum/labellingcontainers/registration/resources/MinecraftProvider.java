package infinituum.labellingcontainers.registration.resources;

import infinituum.labellingcontainers.utils.ContainerResource;

import java.util.function.BiConsumer;

public final class MinecraftProvider implements ContainerResource.IdsProvider, ContainerResource.TagsProvider {
    private final static String NAMESPACE = "minecraft";

    @Override
    public void addIds(BiConsumer<String, String> set) {
        set.accept(NAMESPACE, "furnace");
        set.accept(NAMESPACE, "beacon");
        set.accept(NAMESPACE, "smoker");
        set.accept(NAMESPACE, "blast_furnace");
        set.accept(NAMESPACE, "dispenser");
        set.accept(NAMESPACE, "dropper");
        set.accept(NAMESPACE, "conduit");
        set.accept(NAMESPACE, "brewing_stand");
        set.accept(NAMESPACE, "cauldron");
        set.accept(NAMESPACE, "lectern");
        set.accept(NAMESPACE, "hopper");
        set.accept(NAMESPACE, "daylight_detector");
        set.accept(NAMESPACE, "beehive");
    }

    @Override
    public void addTags(BiConsumer<String, String> set) {
        set.accept(NAMESPACE, "shulker_boxes");
    }
}
