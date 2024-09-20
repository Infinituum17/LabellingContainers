package infinituum.labellingcontainers.huds;

import dev.architectury.event.events.client.ClientGuiEvent.RenderHud;
import infinituum.fastconfigapi.FastConfigs;
import infinituum.labellingcontainers.config.PlayerPreferences;
import infinituum.labellingcontainers.utils.BlockEntityHelper;
import infinituum.labellingcontainers.utils.Taggable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class HudInfoDisplay implements RenderHud {
    @Override
    public void renderHud(GuiGraphics graphics, float tickDelta) {
        Minecraft client = Minecraft.getInstance();
        if (client.screen != null) {
            return;
        }

        HitResult hit = client.hitResult;
        if (hit == null || hit.getType() != HitResult.Type.BLOCK) {
            return;
        }

        BlockHitResult blockHit = (BlockHitResult) hit;
        BlockPos blockPos = blockHit.getBlockPos();
        if (client.level == null) {
            return;
        }

        BlockEntity blockEntity = BlockEntityHelper.locateTargetBlockEntity(client.level, blockPos, client.level.getBlockState(blockPos));

        if (blockEntity instanceof Taggable taggable) {
            FastConfigs.get(PlayerPreferences.class)
                    .getHUDPosition()
                    .render(client, graphics, taggable);
        }
    }
}