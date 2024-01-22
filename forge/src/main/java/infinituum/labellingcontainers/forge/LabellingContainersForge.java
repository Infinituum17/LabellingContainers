package infinituum.labellingcontainers.forge;

import dev.architectury.platform.forge.EventBuses;
import infinituum.labellingcontainers.LabellingContainers;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LabellingContainers.MOD_ID)
public class LabellingContainersForge {
    public LabellingContainersForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(LabellingContainers.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        LabellingContainers.init();
    }
}
