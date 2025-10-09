package infinituum.labellingcontainers.fabric.utils;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ConditionalLoaderMixinPlugin implements IMixinConfigPlugin {
    private static final String MIXINS_FOLDER = "infinituum.labellingcontainers.fabric.mixin.";
    private static final Map<String, String> MIXIN_MODIDS = ImmutableMap.<String, String>builder()
            .put(MIXINS_FOLDER + "storagedelight.BookshelfDoorBlockEntityMixin", "storagedelight")
            .put(MIXINS_FOLDER + "storagedelight.CabinetVariantMixin", "storagedelight")
            .put(MIXINS_FOLDER + "storagedelight.DrawerBlockEntityMixin", "storagedelight")
            .put(MIXINS_FOLDER + "storagedelight.DrawerBooksBlockEntityMixin", "storagedelight")
            .put(MIXINS_FOLDER + "storagedelight.DrawerDoorBlockEntityMixin", "storagedelight")
            .put(MIXINS_FOLDER + "storagedelight.GlassCabinetBlockEntityMixin", "storagedelight")
            .put(MIXINS_FOLDER + "storagedelight.SmallDrawersBlockEntityMixin", "storagedelight")
            .build();

    private static boolean isModLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        final String MODID = MIXIN_MODIDS.get(mixinClassName);

        if (MODID == null) {
            return true;
        }

        return isModLoaded(MODID);
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