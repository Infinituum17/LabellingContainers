package infinituum.labellingcontainers.forge.registration.resources;

import infinituum.labellingcontainers.utils.ContainerResource;

import java.util.function.BiConsumer;

public final class PlatformProvider implements ContainerResource.TagsProvider {
    private static final String NAMESPACE = "forge";

    @Override
    public void addTags(BiConsumer<String, String> set) {
        set.accept(NAMESPACE, "chests");
        set.accept(NAMESPACE, "barrels");
    }
}
