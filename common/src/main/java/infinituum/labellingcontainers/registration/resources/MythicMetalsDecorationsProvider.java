package infinituum.labellingcontainers.registration.resources;

import com.google.common.collect.ImmutableList;
import infinituum.labellingcontainers.utils.ContainerResource;

import java.util.List;
import java.util.function.BiConsumer;

public final class MythicMetalsDecorationsProvider implements ContainerResource.IdsProvider {
    private static final String NAMESPACE = "mythicmetals_decorations";
    private static final List<String> MATERIALS = ImmutableList.<String>builder()
            .add("adamantite").add("aquarium").add("banglum")
            .add("bronze").add("carmot").add("celestium")
            .add("durasteel").add("hallowed").add("kyber")
            .add("manganese").add("metallurgium").add("midas_gold")
            .add("mythril").add("orichalcum").add("osmium")
            .add("palladium").add("platinum").add("prometheum")
            .add("quadrillum").add("runite").add("silver")
            .add("star_platinum").add("steel").add("stormyx")
            .build();

    @Override
    public void addIds(BiConsumer<String, String> set) {
        for (String material : MATERIALS) {
            set.accept(NAMESPACE, material + "_chest");
        }
    }
}
