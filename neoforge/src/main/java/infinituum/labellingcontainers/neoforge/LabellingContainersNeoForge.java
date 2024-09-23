package infinituum.labellingcontainers.neoforge;

import infinituum.labellingcontainers.LabellingContainers;
import infinituum.labellingcontainers.neoforge.events.ScreenRegistration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;

@Mod(MOD_ID)
public class LabellingContainersNeoForge {
    public LabellingContainersNeoForge(IEventBus modBus) {
        // FIXME: Remove this line as soon as a new release fixes this bug (NeoForge screen registration)
        modBus.register(new ScreenRegistration());

        LabellingContainers.init();
    }
}
