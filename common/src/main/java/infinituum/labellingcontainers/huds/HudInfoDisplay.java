package infinituum.labellingcontainers.huds;

import dev.architectury.event.events.client.ClientGuiEvent.RenderHud;
import infinituum.labellingcontainers.PlatformHelper;
import infinituum.labellingcontainers.utils.BlockEntityHelper;
import infinituum.labellingcontainers.utils.Taggable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class HudInfoDisplay implements RenderHud {
    @Override
    public void renderHud(GuiGraphics context, float tickDelta) {
        Minecraft client = Minecraft.getInstance();
        if (client.screen != null) return;

        HitResult hit = client.hitResult;
        if (hit == null || hit.getType() != HitResult.Type.BLOCK) return;

        BlockHitResult blockHit = (BlockHitResult) hit;
        BlockPos blockPos = blockHit.getBlockPos();
        if (client.level == null) return;

        BlockEntity blockEntity = PlatformHelper.locateTargetBlockEntity(client.level, blockPos, client.level.getBlockState(blockPos));

        if (blockEntity instanceof Taggable labelable) {
            Item displayItem = (labelable.labellingcontainers$getDisplayItem() != null) ? labelable.labellingcontainers$getDisplayItem() : client.level.getBlockState(blockPos).getBlock().asItem();

            int width = client.getWindow().getGuiScaledWidth();
            int height = client.getWindow().getGuiScaledHeight();
            int fontHeight = client.font.lineHeight;

            int x = width / 2;
            int y = height / 2;
            int leftPadding = 10;

            context.renderItem(new ItemStack(displayItem), x + leftPadding, y - 8);
            context.drawString(client.font, labelable.labellingcontainers$getLabel(), x + leftPadding * 3, (y + 1) - fontHeight / 2, 0xFFFFFFFF);
        }
    }
}