package infinituum.labellingcontainers.registration.resources;

import infinituum.labellingcontainers.utils.ContainerResource;

import java.util.function.BiConsumer;

public final class SupplementariesProvider implements ContainerResource.IdsProvider, ContainerResource.TagsProvider {
    private static final String NAMESPACE = "supplementaries";

    @Override
    public void addIds(BiConsumer<String, String> set) {
        set.accept(NAMESPACE, "jar");
        set.accept(NAMESPACE, "sack");
        set.accept(NAMESPACE, "safe");
        set.accept(NAMESPACE, "pedestal");
        set.accept(NAMESPACE, "planter");
        set.accept(NAMESPACE, "urn");
        set.accept(NAMESPACE, "lock_block");
    }

    @Override
    public void addTags(BiConsumer<String, String> set) {
        set.accept(NAMESPACE, "presents");
        set.accept(NAMESPACE, "trapped_presents");
    }
}
