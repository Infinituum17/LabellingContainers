package infinituum.labellingcontainers.registration.resources;

import infinituum.labellingcontainers.utils.ContainerResource;

import java.util.function.BiConsumer;

public final class NetherChestedProvider implements ContainerResource.IdsProvider {
    private final static String NAMESPACE = "netherchested";

    @Override
    public void addIds(BiConsumer<String, String> set) {
        set.accept(NAMESPACE, "nether_chest");
    }
}
