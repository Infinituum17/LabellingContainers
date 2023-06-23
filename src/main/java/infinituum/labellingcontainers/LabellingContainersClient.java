package infinituum.labellingcontainers;

import infinituum.labellingcontainers.guis.LabelPrinterGui;
import infinituum.labellingcontainers.huds.HudInfoDisplay;
import infinituum.labellingcontainers.registration.ScreenRegistration;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class LabellingContainersClient implements ClientModInitializer {
	public static final String MODID = "labellingcontainers";

	@Override
	public void onInitializeClient() {
		HudRenderCallback.EVENT.register(new HudInfoDisplay());

		HandledScreens.register(ScreenRegistration.LABEL_PRINTER_SCREEN_HANDLER, LabelPrinterGui::new);
	}
}