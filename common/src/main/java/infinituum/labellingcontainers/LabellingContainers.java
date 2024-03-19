package infinituum.labellingcontainers;

import infinituum.labellingcontainers.registration.CommandRegistration;
import infinituum.labellingcontainers.registration.EventHandlersRegistration;
import infinituum.labellingcontainers.registration.ItemRegistration;
import infinituum.labellingcontainers.registration.ScreenRegistration;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class LabellingContainers {
    public static final String MOD_ID = "labellingcontainers";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static Map<String, Block> TAGGABLE_BLOCKS = new HashMap<>();
    
    public static void init() {
        LOGGER.info("Initializing mod...");

        CommandRegistration.init();
        ItemRegistration.init();
        ScreenRegistration.init();
        EventHandlersRegistration.init();

        LabellingContainersClientSetup.init();
    }
}
