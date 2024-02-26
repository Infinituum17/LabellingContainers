package infinituum.labellingcontainers.fabric.utils;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public final class ConditionalLoaderMixinPlugin implements IMixinConfigPlugin {
    private static final String MIXINS_FOLDER = "infinituum.labellingcontainers.fabric.mixin.";
    private static final Map<String, Supplier<Boolean>> CONDITIONS = ImmutableMap.<String, Supplier<Boolean>>builder()
            .put(MIXINS_FOLDER + "ironchests.GenericChestBlockMixin", isModLoaded("ironchests"))
            .put(MIXINS_FOLDER + "ironchests.GenericChestBlockEntityMixin", isModLoaded("ironchests"))
            .put(MIXINS_FOLDER + "mythicmetals_decorations.MythicChestBlockMixin", isModLoaded("mythicmetals_decorations"))
            .put(MIXINS_FOLDER + "mythicmetals_decorations.MythicChestBlockEntityMixin", isModLoaded("mythicmetals_decorations"))
            .put(MIXINS_FOLDER + "echochest.EchoChestBlockEntityMixin", isModLoaded("echochest"))
            .put(MIXINS_FOLDER + "echochest.EchoChestBlockMixin", isModLoaded("echochest"))
            .put(MIXINS_FOLDER + "netherchested.NetherChestBlockEntityMixin", isModLoaded("netherchested"))
            .put(MIXINS_FOLDER + "netherchested.NetherChestBlockMixin", isModLoaded("netherchested"))
            .put(MIXINS_FOLDER + "more_chests.CustomChestBlockItemMixin", isModLoaded("more_chests"))
            .put(MIXINS_FOLDER + "more_chests.CustomChestBlockMixin", isModLoaded("more_chests"))
            .put(MIXINS_FOLDER + "compact_storage.CompactChestBlockMixin", isModLoaded("compact_storage"))
            .put(MIXINS_FOLDER + "compact_storage.CompactBarrelBlockMixin", isModLoaded("compact_storage"))
            .put(MIXINS_FOLDER + "compact_storage.CompactChestBlockEntityMixin", isModLoaded("compact_storage"))
            .put(MIXINS_FOLDER + "compact_storage.CompactBarrelBlockEntityMixin", isModLoaded("compact_storage"))
            .build();

    private static Supplier<Boolean> isModLoaded(String modid) {
        return () -> FabricLoader.getInstance().isModLoaded(modid);
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return CONDITIONS.getOrDefault(mixinClassName, () -> true).get();
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}