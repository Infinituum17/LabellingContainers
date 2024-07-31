package infinituum.labellingcontainers.fabric.registration.resources;

import infinituum.labellingcontainers.utils.ContainerResource;

import java.util.function.BiConsumer;

public final class PlatformProvider implements ContainerResource.TagsProvider {
    private static final String NAMESPACE = "c";

    @Override
    public void addTags(BiConsumer<String, String> set) {
        set.accept(NAMESPACE, "chests");
        set.accept(NAMESPACE, "shulker_boxes");
        set.accept(NAMESPACE, "barrels");
        set.accept(NAMESPACE, "barrel");
        set.accept(NAMESPACE, "wooden_barrels");
    }
}
