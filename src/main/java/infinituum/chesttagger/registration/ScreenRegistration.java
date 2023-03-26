package infinituum.chesttagger.registration;

import infinituum.chesttagger.screens.LabellingMachineScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import static infinituum.chesttagger.ChestTagger.MODID;

public class ScreenRegistration {
    public static final ScreenHandlerType<LabellingMachineScreenHandler> LABELLING_MACHINE_SCREEN_HANDLER = new ScreenHandlerType<>(LabellingMachineScreenHandler::new, FeatureSet.empty());

    public static void init() {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(MODID, "labelling_machine_screen"), LABELLING_MACHINE_SCREEN_HANDLER);
    }
}
