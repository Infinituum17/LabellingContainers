package infinituum.labellingcontainers.registration;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import infinituum.labellingcontainers.registration.component_types.LabelProperties;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;

import java.util.function.UnaryOperator;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;

public final class DataComponentRegistration {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(MOD_ID, Registries.DATA_COMPONENT_TYPE);
    public static final RegistrySupplier<DataComponentType<LabelProperties>> LABEL_COMPONENT_TYPE = register(
            "label",
            (builder) -> builder
                    .persistent(LabelProperties.CODEC)
                    .networkSynchronized(LabelProperties.NETWORK_CODEC)
                    .cacheEncoding()
    );

    public static void init() {
        DATA_COMPONENT_TYPES.register();
    }

    public static <T> RegistrySupplier<DataComponentType<T>> register(String id, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return DATA_COMPONENT_TYPES.register(id, () -> builder.apply(DataComponentType.builder()).build());
    }
}
