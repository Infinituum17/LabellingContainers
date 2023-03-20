package infinituum.chesttagger;

import infinituum.chesttagger.huds.HudInfoDisplay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class ChestTaggerClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HudRenderCallback.EVENT.register(new HudInfoDisplay());
	}
}