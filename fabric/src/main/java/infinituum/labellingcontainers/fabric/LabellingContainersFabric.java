package infinituum.labellingcontainers.fabric;

import infinituum.labellingcontainers.LabellingContainers;
import infinituum.labellingcontainers.fabric.events.BlockPlaceEventHandler;
import infinituum.labellingcontainers.fabric.events.custom.BlockPlaceCallback;
import net.fabricmc.api.ModInitializer;

public final class LabellingContainersFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        LabellingContainers.init();

        BlockPlaceCallback.EVENT.register(BlockPlaceEventHandler::handle);
    }
}
