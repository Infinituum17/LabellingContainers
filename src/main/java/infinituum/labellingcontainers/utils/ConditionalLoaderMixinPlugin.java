package infinituum.labellingcontainers.utils;

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
    private static final Map<String, Supplier<Boolean>> CONDITIONS = ImmutableMap.of(
            "infinituum.labellingcontainers.mixin.GenericChestBlockMixin", () -> FabricLoader.getInstance().isModLoaded("ironchests"),
            "infinituum.labellingcontainers.mixin.GenericChestBlockEntityMixin", () -> FabricLoader.getInstance().isModLoaded("ironchests"),
            "infinituum.labellingcontainers.mixin.MythicChestBlockMixin", () -> FabricLoader.getInstance().isModLoaded("mythicmetals_decorations"),
            "infinituum.labellingcontainers.mixin.MythicChestBlockEntityMixin", () -> FabricLoader.getInstance().isModLoaded("mythicmetals_decorations")
    );

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
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public void onLoad(String mixinPackage) {}

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}