package infinituum.chesttagger;

import infinituum.chesttagger.registration.CommandRegistration;
import infinituum.chesttagger.registration.EventHandlersRegistration;
import infinituum.chesttagger.registration.ItemRegistration;
import infinituum.chesttagger.registration.ScreenRegistration;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChestTagger implements ModInitializer {
	public static final String MODID = "chesttagger";
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