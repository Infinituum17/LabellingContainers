package infinituum.labellingcontainers.registration.resources;

import com.google.common.collect.ImmutableList;
import infinituum.labellingcontainers.utils.ContainerResource;

import java.util.List;
import java.util.function.BiConsumer;

public final class IronChestProvider implements ContainerResource.IdsProvider {
    private static final String NAMESPACE = "ironchest";
    private static final List<String> MATERIALS = ImmutableList.<String>builder()
            .add("iron")
            .add("gold")
            .add("diamond")
            .add("copper")
            .add("crystal")
            .add("obsidian")
            .add("dirt")
            .build();

    @Override
    public void addIds(BiConsumer<String, String> set) {
        for (String material : MATERIALS) {
            set.accept(NAMESPACE, material + "_chest");
            set.accept(NAMESPACE, "trapped_" + material + "_chest");
        }
    }
}
