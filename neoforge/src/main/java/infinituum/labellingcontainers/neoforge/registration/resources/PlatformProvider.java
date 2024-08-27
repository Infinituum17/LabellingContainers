package infinituum.labellingcontainers.neoforge.registration.resources;

import infinituum.labellingcontainers.utils.ContainerResource;

import java.util.function.BiConsumer;

public final class PlatformProvider implements ContainerResource.TagsProvider {
    private static final String NAMESPACE = "c";

    @Override
    public void addTags(BiConsumer<String, String> set) {
        set.accept(NAMESPACE, "chests");
        set.accept(NAMESPACE, "barrels");
        set.accept(NAMESPACE, "storage_blocks");
    }
}
