package infinituum.labellingcontainers.registration.resources;

import com.google.common.collect.ImmutableList;
import infinituum.labellingcontainers.utils.ContainerResource;

import java.util.List;
import java.util.function.BiConsumer;

public final class FantasyFurniture implements ContainerResource.IdsProvider {
    private static final String NAMESPACE = "fantasyfurniture";
    private static final List<String> MATERIALS = ImmutableList.<String>builder()
            .add("nordic")
            .add("venthyr")
            .add("dunmer")
            .add("bone/skeleton")
            .add("bone/wither")
            .add("royal")
            .add("necrolord")
            .build();

    @Override
    public void addIds(BiConsumer<String, String> set) {
        for (String material : MATERIALS) {
            set.accept(NAMESPACE, material + "/drawer");
            set.accept(NAMESPACE, material + "/bookshelf");
            set.accept(NAMESPACE, material + "/chest");
            set.accept(NAMESPACE, material + "/dresser");
            set.accept(NAMESPACE, material + "/wardrobe_bottom");
            set.accept(NAMESPACE, material + "/lockbox");
            set.accept(NAMESPACE, material + "/counter");
            set.accept(NAMESPACE, material + "/desk_left");
            set.accept(NAMESPACE, material + "/desk_right");
        }
    }
}
