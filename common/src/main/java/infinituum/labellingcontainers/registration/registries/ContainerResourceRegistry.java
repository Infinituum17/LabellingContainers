package infinituum.labellingcontainers.registration.registries;

import infinituum.labellingcontainers.utils.ContainerResource;
import infinituum.labellingcontainers.utils.ContainerSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class ContainerResourceRegistry {
    private static final List<ContainerResource> PROVIDERS = new ArrayList<>();

    public static void register(ContainerResource provider) {
        PROVIDERS.add(provider);
    }

    public static Set<String> getIds() {
        ContainerSet containerSet = new ContainerSet();

        for (ContainerResource provider : PROVIDERS) {
            if (provider instanceof ContainerResource.IdsProvider idProvider) {
                idProvider.addIds(containerSet::add);
            }
        }

        return containerSet.toSet();
    }

    public static Set<String> getTags() {
        ContainerSet containerSet = new ContainerSet();

        for (ContainerResource provider : PROVIDERS) {
            if (provider instanceof ContainerResource.TagsProvider tagProvider) {
                tagProvider.addTags(containerSet::add);
            }
        }

        return containerSet.toSet();
    }
}
