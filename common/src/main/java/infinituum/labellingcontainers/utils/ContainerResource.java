package infinituum.labellingcontainers.utils;

import java.util.function.BiConsumer;

public interface ContainerResource {
    interface IdsProvider extends ContainerResource {
        void addIds(BiConsumer<String, String> set);
    }

    interface TagsProvider extends ContainerResource {
        void addTags(BiConsumer<String, String> set);
    }
}
