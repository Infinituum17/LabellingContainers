package infinituum.labellingcontainers.config;

import com.google.common.collect.ImmutableSet;

import java.util.Set;


public class TaggableBlocksConfig {
    /**
     * '{@code true}' if only certain blocks can be tagged, '{@code false}' otherwise<br/>
     * <b>Default</b>: {@code true}
     */
    public boolean hasTagsLimit = true;
    /**
     * If {@link TaggableBlocksConfig#hasTagsLimit} is '{@code true}', then only this variable's ids are taggable
     */
    public Set<String> taggableIds = ImmutableSet.<String>builder()
            .add("minecraft:chest")
            .add("minecraft:furnace")
            .add("minecraft:beacon")
            .add("minecraft:ender_chest")
            .add("minecraft:barrel")
            .add("minecraft:smoker")
            .add("minecraft:blast_furnace")
            .add("minecraft:dispenser")
            .add("minecraft:dropper")
            .add("minecraft:trapped_chest")
            .add("minecraft:conduit")
            .add("minecraft:brewing_stand")
            .add("minecraft:cauldron")
            .build();

    public TaggableBlocksConfig(Set<String> taggableIds) {
        this.taggableIds = taggableIds;
    }

    public TaggableBlocksConfig() {
    }

    public boolean hasId(String key) {
        return taggableIds.contains(key);
    }
}
