package infinituum.labellingcontainers.forge;

import dev.architectury.platform.forge.EventBuses;
import infinituum.labellingcontainers.LabellingContainers;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LabellingContainers.MOD_ID)
public final class LabellingContainersForge {
    public LabellingContainersForge() {
        EventBuses.registerModEventBus(LabellingContainers.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        LabellingContainers.init();
    }
}
