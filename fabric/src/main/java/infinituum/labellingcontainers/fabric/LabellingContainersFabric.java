package infinituum.labellingcontainers.fabric;

import infinituum.labellingcontainers.LabellingContainers;
import infinituum.labellingcontainers.fabric.events.BlockPlaceCallback;
import infinituum.labellingcontainers.fabric.events.BlockPlaceEventHandler;
import net.fabricmc.api.ModInitializer;

public class LabellingContainersFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        LabellingContainers.init();

        BlockPlaceCallback.EVENT.register(BlockPlaceEventHandler::handle);
    }
}
