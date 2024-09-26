package infinituum.labellingcontainers.config;

import infinituum.fastconfigapi.api.annotations.FastConfig;
import infinituum.fastconfigapi.api.annotations.Loader;
import infinituum.fastconfigapi.api.serializers.JSONSerializer;
import infinituum.labellingcontainers.registration.registries.ContainerResourceRegistry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.Set;
import java.util.stream.Stream;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;

@FastConfig(
        subdirectory = MOD_ID,
        loader = @Loader(
                type = Loader.Type.URL,
                target = "https://raw.githubusercontent.com/Infinituum17/LabellingContainers/refs/heads/main/defaults/config/compatible-containers.json",
                deserializer = JSONSerializer.class
        )
)
public final class CompatibleContainers {
    /**
     * '{@code true}' if only certain blocks can be tagged, '{@code false}' otherwise<br/>
     * <b>Default</b>: {@code true}
     */
    private final boolean limitedContainers = true;

    /**
     * If {@link CompatibleContainers#limitedContainers} is '{@code true}', then only this variable's ids are taggable
     */
    private Set<String> ids = ContainerResourceRegistry.getIds();

    /**
     * If {@link CompatibleContainers#limitedContainers} is '{@code true}', then only this variable's block tags are taggable
     */
    private Set<String> blockTags = ContainerResourceRegistry.getTags();

    public boolean isLimited() {
        return limitedContainers;
    }

    public boolean has(String key) {
        return ids.contains(key) || blockTags.contains(key);
    }

    public boolean hasAnyTag(Stream<TagKey<Block>> tags) {
        return tags.anyMatch(blockTagKey -> blockTags.contains(blockTagKey.location().toString()));
    }

    public void addId(String id) {
        ids.add(id);
    }

    public void addTag(String tag) {
        blockTags.add(tag);
    }

    public void removeId(String id) {
        ids.remove(id);
    }

    public void removeTag(String tag) {
        blockTags.remove(tag);
    }

    public Set<String> getIds() {
        return this.ids;
    }

    public void setIds(Set<String> set) {
        this.ids = set;
    }

    public Set<String> getTags() {
        return this.blockTags;
    }

    public void setTags(Set<String> set) {
        this.blockTags = set;
    }
}
