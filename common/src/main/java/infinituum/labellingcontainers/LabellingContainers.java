package infinituum.labellingcontainers;

import infinituum.labellingcontainers.registration.CommandRegistration;
import infinituum.labellingcontainers.registration.EventHandlersRegistration;
import infinituum.labellingcontainers.registration.ItemRegistration;
import infinituum.labellingcontainers.registration.ScreenRegistration;

public class LabellingContainers {
    public static final String MOD_ID = "labellingcontainers";
    
    public static void init() {
        CommandRegistration.init();
        ItemRegistration.init();
        ScreenRegistration.init();
        EventHandlersRegistration.init();

        LabellingContainersClientSetup.init();
    }
}
