package infinituum.labellingcontainers;

import infinituum.labellingcontainers.registration.CommandRegistration;
import infinituum.labellingcontainers.registration.ItemRegistration;
import infinituum.labellingcontainers.registration.ScreenRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LabellingContainers {
    public static final String MOD_ID = "labellingcontainers";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        LOGGER.info("Initializing mod...");

        CommandRegistration.init();
        ItemRegistration.init();
        ScreenRegistration.init();

        LabellingContainersSetup.initServer();
        LabellingContainersSetup.initClient();

        LabellingContainersConfig.commonInit();
    }
}
