package infinituum.labellingcontainers.registration.resources;

import com.google.common.collect.ImmutableList;
import infinituum.labellingcontainers.utils.ContainerResource;

import java.util.List;
import java.util.function.BiConsumer;

public final class SophisticatedStorageProvider implements ContainerResource.IdsProvider {
    private static final String NAMESPACE = "sophisticatedstorage";
    private static final List<String> MATERIALS = ImmutableList.<String>builder()
            .add("iron")
            .add("copper")
            .add("gold")
            .add("diamond")
            .add("netherite")
            .build();

    @Override
    public void addIds(BiConsumer<String, String> set) {
        for (int i = 0; i < 4; i++) {
            set.accept(NAMESPACE, "limited_barrel_" + (i + 1));
        }

        set.accept(NAMESPACE, "shulker_box");

        for (String material : MATERIALS) {
            for (int i = 0; i < 4; ++i) {
                set.accept(NAMESPACE, "limited_" + material + "_barrel_" + (i + 1));
            }

            set.accept(NAMESPACE, material + "_shulker_box");
        }
    }
}
