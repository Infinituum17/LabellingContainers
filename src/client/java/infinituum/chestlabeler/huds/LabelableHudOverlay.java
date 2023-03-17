package infinituum.chestlabeler.huds;

import infinituum.chestlabeler.utils.Labelable;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class LabelableHudOverlay implements HudRenderCallback {
    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if(client == null || client.currentScreen != null) return;

        HitResult hit = client.crosshairTarget;
        if(hit == null || hit.getType() != HitResult.Type.BLOCK) return;

        BlockHitResult blockHit = (BlockHitResult) hit;
        BlockPos blockPos = blockHit.getBlockPos();
        if(client.world == null) return;

        BlockEntity blockEntity = client.world.getBlockEntity(blockPos);
        if(blockEntity == null) return;

        if(blockEntity instanceof Labelable labelable) {
            Item displayItem = (labelable.getLabelDisplayItem() != null) ? labelable.getLabelDisplayItem() : client.world.getBlockState(blockPos).getBlock().asItem();

            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();
            int fontHeight = client.textRenderer.fontHeight;

            int x = width / 2;
            int y = height / 2;
            int leftPadding = 10;

            matrixStack.push();

            client.getItemRenderer().renderGuiItemIcon(matrixStack, new ItemStack(displayItem), x + leftPadding, y - 8);
            DrawableHelper.drawTextWithShadow(matrixStack, client.textRenderer, labelable.getLabel(), x + leftPadding * 3, (y + 1) - fontHeight / 2, 0xFFFFFFFF);

            matrixStack.pop();
        }
    }
}
