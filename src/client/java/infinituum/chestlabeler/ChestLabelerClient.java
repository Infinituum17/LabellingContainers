package infinituum.chestlabeler;

import infinituum.chestlabeler.huds.LabelableHudOverlay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class ChestLabelerClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HudRenderCallback.EVENT.register(new LabelableHudOverlay());
	}
}