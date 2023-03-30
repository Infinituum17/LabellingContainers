package infinituum.labellingcontainers;

import infinituum.labellingcontainers.registration.CommandRegistration;
import infinituum.labellingcontainers.registration.EventHandlersRegistration;
import infinituum.labellingcontainers.registration.ItemRegistration;
import infinituum.labellingcontainers.registration.ScreenRegistration;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LabellingContainers implements ModInitializer {
	public static final String MODID = "labellingcontainers";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing...");

		CommandRegistration.init();
		ItemRegistration.init();
		ScreenRegistration.init();
		EventHandlersRegistration.init();
	}
}