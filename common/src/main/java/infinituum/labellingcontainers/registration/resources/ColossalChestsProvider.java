package infinituum.labellingcontainers.registration.resources;

import com.google.common.collect.ImmutableList;
import infinituum.labellingcontainers.utils.ContainerResource;

import java.util.List;
import java.util.function.BiConsumer;

public final class ColossalChestsProvider implements ContainerResource.IdsProvider {
    private static final String NAMESPACE = "colossalchests";
    private static final List<String> MATERIALS = ImmutableList.<String>builder()
            .add("wood")
            .add("copper")
            .add("iron")
            .add("silver")
            .add("gold")
            .add("diamond")
            .add("obsidian")
            .build();

    @Override
    public void addIds(BiConsumer<String, String> set) {
        for (String material : MATERIALS) {
            set.accept(NAMESPACE, "chest_wall_" + material);
            set.accept(NAMESPACE, "colossal_chest_" + material);
            set.accept(NAMESPACE, "interface_" + material);
        }

        set.accept(NAMESPACE, "uncolossal_chest");
    }
}
