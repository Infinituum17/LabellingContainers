package infinituum.labellingcontainers.forge.utils;

import com.google.common.collect.ImmutableMap;
import net.minecraftforge.fml.ModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public final class ConditionalLoaderMixinPlugin implements IMixinConfigPlugin {
    private static final Map<String, Supplier<Boolean>> CONDITIONS = ImmutableMap.of(
            "infinituum.labellingcontainers.forge.mixin.GenericChestBlockMixin", () -> ModList.get().isLoaded("ironchests"),
            "infinituum.labellingcontainers.forge.mixin.GenericChestBlockEntityMixin", () -> ModList.get().isLoaded("ironchests"),
            "infinituum.labellingcontainers.forge.mixin.AbstractIronChestBlockMixin", () -> ModList.get().isLoaded("ironchest"),
            "infinituum.labellingcontainers.forge.mixin.AbstractIronChestBlockEntityMixin", () -> ModList.get().isLoaded("ironchest")
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