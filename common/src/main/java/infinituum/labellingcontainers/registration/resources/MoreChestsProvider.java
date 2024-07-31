package infinituum.labellingcontainers.registration.resources;

import infinituum.labellingcontainers.utils.ContainerResource;

import java.util.function.BiConsumer;

public final class MoreChestsProvider implements ContainerResource.TagsProvider {
    private static final String NAMESPACE = "more_chests";

    @Override
    public void addTags(BiConsumer<String, String> set) {
        set.accept(NAMESPACE, "upgradeable_chests");
    }
}
