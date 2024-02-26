package infinituum.labellingcontainers.neoforge;

import infinituum.labellingcontainers.LabellingContainers;
import net.neoforged.fml.common.Mod;

@Mod(LabellingContainers.MOD_ID)
public class LabellingContainersNeoForge {
    public LabellingContainersNeoForge() {
        // Submit our event bus to let architectury register our content on the right time

        // EventBuses.registerModEventBus(LabellingContainers.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        LabellingContainers.init();
    }
}
