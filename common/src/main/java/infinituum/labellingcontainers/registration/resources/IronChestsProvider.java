package infinituum.labellingcontainers.registration.resources;

import infinituum.labellingcontainers.utils.ContainerResource;

import java.util.function.BiConsumer;

public final class IronChestsProvider implements ContainerResource.TagsProvider {
    private final static String NAMESPACE = "ironchests";

    @Override
    public void addTags(BiConsumer<String, String> set) {
        set.accept(NAMESPACE, "chests");
        set.accept(NAMESPACE, "barrels");
    }
}
