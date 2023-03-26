package infinituum.chesttagger;

import infinituum.chesttagger.guis.LabellingMachineGui;
import infinituum.chesttagger.huds.HudInfoDisplay;
import infinituum.chesttagger.registration.ScreenRegistration;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ChestTaggerClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HudRenderCallback.EVENT.register(new HudInfoDisplay());

		HandledScreens.register(ScreenRegistration.LABELLING_MACHINE_SCREEN_HANDLER, LabellingMachineGui::new);
	}
}