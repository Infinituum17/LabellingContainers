package infinituum.labellingcontainers.huds;

import dev.architectury.event.events.client.ClientGuiEvent.RenderHud;
import infinituum.labellingcontainers.utils.Taggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class HudInfoDisplay implements RenderHud {
    @Override
    public void renderHud(MatrixStack context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if(client == null || client.currentScreen != null) return;

        HitResult hit = client.crosshairTarget;
        if(hit == null || hit.getType() != HitResult.Type.BLOCK) return;

        BlockHitResult blockHit = (BlockHitResult) hit;
        BlockPos blockPos = blockHit.getBlockPos();
        if(client.world == null) return;

        BlockEntity blockEntity = client.world.getBlockEntity(blockPos);
        if(blockEntity == null) return;

        if(blockEntity instanceof Taggable labelable) {
            Item displayItem = (labelable.labellingcontainers$getDisplayItem() != null) ? labelable.labellingcontainers$getDisplayItem() : client.world.getBlockState(blockPos).getBlock().asItem();

            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();
            int fontHeight = client.textRenderer.fontHeight;

            int x = width / 2;
            int y = height / 2;
            int leftPadding = 10;

            context.push();

            client.getItemRenderer().renderGuiItemIcon(new ItemStack(displayItem), x + leftPadding, y - 8);
            DrawableHelper.drawTextWithShadow(context, client.textRenderer, labelable.labellingcontainers$getLabel(), x + leftPadding * 3, (y + 1) - fontHeight / 2, 0xFFFFFFFF);

            context.pop();
        }
    }
}