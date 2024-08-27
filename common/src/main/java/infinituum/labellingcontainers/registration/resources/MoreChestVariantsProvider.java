package infinituum.labellingcontainers.registration.resources;

import com.google.common.collect.ImmutableList;
import infinituum.labellingcontainers.utils.ContainerResource;

import java.util.List;
import java.util.function.BiConsumer;

public final class MoreChestVariantsProvider implements ContainerResource.IdsProvider {
    private static final String NAMESPACE = "lolmcv";
    private static final List<String> WOOD_TYPES = ImmutableList.<String>builder()
            .add("crimson")
            .add("bamboo")
            .add("cherry")
            .add("mangrove")
            .add("dark_oak")
            .add("acacia")
            .add("jungle")
            .add("birch")
            .add("spruce")
            .add("warped")
            .add("oak")
            .build();

    @Override
    public void addIds(BiConsumer<String, String> set) {
        for (String wood : WOOD_TYPES) {
            set.accept(NAMESPACE, wood + "_chest");
            set.accept(NAMESPACE, wood + "_trapped_chest");
        }
    }
}
