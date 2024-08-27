package infinituum.labellingcontainers.registration.resources;

import infinituum.labellingcontainers.utils.ContainerResource;

import java.util.function.BiConsumer;

public final class EchoChestProvider implements ContainerResource.IdsProvider {
    private final static String NAMESPACE = "echochest";

    @Override
    public void addIds(BiConsumer<String, String> set) {
        set.accept(NAMESPACE, "echo_chest");
    }
}
