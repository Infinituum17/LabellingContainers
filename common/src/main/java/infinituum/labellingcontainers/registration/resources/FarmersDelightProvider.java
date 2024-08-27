package infinituum.labellingcontainers.registration.resources;

import com.google.common.collect.ImmutableList;
import infinituum.labellingcontainers.utils.ContainerResource;

import java.util.List;
import java.util.function.BiConsumer;

public final class FarmersDelightProvider implements ContainerResource.IdsProvider {
    private static final String NAMESPACE = "farmersdelight";
    private static final List<String> WOOD_TYPES = ImmutableList.<String>builder()
            .add("acacia")
            .add("birch")
            .add("crimson")
            .add("dark_oak")
            .add("jungle")
            .add("mangrove")
            .add("oak")
            .add("spruce")
            .add("warped")
            .add("bamboo")
            .add("cherry")
            .build();

    @Override
    public void addIds(BiConsumer<String, String> set) {
        for (String wood : WOOD_TYPES) {
            set.accept(NAMESPACE, wood + "_cabinet");
        }

        set.accept(NAMESPACE, "basket");
    }
}
