package infinituum.labellingcontainers.registration;

import infinituum.labellingcontainers.PlatformHelper;
import infinituum.labellingcontainers.registration.registries.ContainerResourceRegistry;
import infinituum.labellingcontainers.registration.resources.*;

public final class ContainerResourcesRegistration {
    public static void init() {
        ContainerResourceRegistry.register(PlatformHelper.getPlatformSpecificProvider());
        ContainerResourceRegistry.register(new ColossalChestsProvider());
        ContainerResourceRegistry.register(new CompactStorageProvider());
        ContainerResourceRegistry.register(new EchoChestProvider());
        ContainerResourceRegistry.register(new FantasyFurniture());
        ContainerResourceRegistry.register(new FarmersDelightProvider());
        ContainerResourceRegistry.register(new IronChestProvider());
        ContainerResourceRegistry.register(new IronChestsProvider());
        ContainerResourceRegistry.register(new MinecraftProvider());
        ContainerResourceRegistry.register(new MoreChestsProvider());
        ContainerResourceRegistry.register(new MoreChestVariantsProvider());
        ContainerResourceRegistry.register(new MythicMetalsDecorationsProvider());
        ContainerResourceRegistry.register(new NetherChestedProvider());
        ContainerResourceRegistry.register(new SophisticatedStorageProvider());
        ContainerResourceRegistry.register(new SupplementariesProvider());
    }
}
