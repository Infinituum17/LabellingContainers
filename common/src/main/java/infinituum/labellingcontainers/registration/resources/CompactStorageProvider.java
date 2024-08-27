package infinituum.labellingcontainers.registration.resources;

import infinituum.labellingcontainers.utils.ContainerResource;

import java.util.function.BiConsumer;

public final class CompactStorageProvider implements ContainerResource.TagsProvider {
    private static final String NAMESPACE = "compact_storage";

    @Override
    public void addTags(BiConsumer<String, String> set) {
        set.accept(NAMESPACE, "compact_chest");
        set.accept(NAMESPACE, "compact_barrel");
    }
}
