package infinituum.labellingcontainers.fabric;

import infinituum.labellingcontainers.LabellingContainers;
import net.fabricmc.api.ModInitializer;

public class LabellingContainersFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        LabellingContainers.init();
    }
}
