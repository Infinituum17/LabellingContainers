package infinituum.labellingcontainers;

import infinituum.labellingcontainers.network.Packets;
import infinituum.labellingcontainers.registration.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LabellingContainers {
    public static final String MOD_ID = "labellingcontainers";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        LOGGER.info("Initializing mod...");

        ContainerResourcesRegistration.init();
        DataComponentRegistration.init();
        CommandRegistration.init();
        ItemRegistration.init();
        ScreenRegistration.init();

        Packets.init();
        LabellingContainersSetup.initClient();
    }
}