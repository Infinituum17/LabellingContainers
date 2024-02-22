package infinituum.labellingcontainers.forge.mixin;

import dev.architectury.event.events.client.ClientGuiEvent.RenderHud;
import infinituum.labellingcontainers.huds.HudInfoDisplay;
import infinituum.labellingcontainers.utils.Taggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.cyclops.colossalchests.block.ChestWall;
import org.cyclops.colossalchests.block.ColossalChest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(HudInfoDisplay.class)
public class HudInfoDisplayMixin implements RenderHud {
    /**
     * @author Infinituum17
     * @reason Advanced interactions with Forge-only mods
     */
    @Overwrite
    public void renderHud(MatrixStack context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.currentScreen != null) return;

        HitResult hit = client.crosshairTarget;
        if (hit == null || hit.getType() != HitResult.Type.BLOCK) return;

        BlockHitResult blockHit = (BlockHitResult) hit;
        BlockPos blockPos = blockHit.getBlockPos();
        if (client.world == null) return;

        BlockState blockState = client.world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        BlockEntity blockEntity = null;

        if(block instanceof ChestWall && blockState.get(ColossalChest.ENABLED)) {
            BlockPos corePos = ColossalChest.getCoreLocation(((ChestWall) block).getMaterial(), client.world, blockPos);
            if (corePos != null) blockEntity = client.world.getBlockEntity(corePos);
        } else {
            blockEntity = client.world.getBlockEntity(blockPos);
        }

        if (blockEntity instanceof Taggable taggable) {
            Item displayItem = (taggable.labellingcontainers$getDisplayItem() != null) ? taggable.labellingcontainers$getDisplayItem() : client.world.getBlockState(blockPos).getBlock().asItem();

            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();
            int fontHeight = client.textRenderer.fontHeight;

            int x = width / 2;
            int y = height / 2;
            int leftPadding = 10;

            context.push();

            client.getItemRenderer().renderGuiItemIcon(new ItemStack(displayItem), x + leftPadding, y - 8);
            DrawableHelper.drawTextWithShadow(context, client.textRenderer, taggable.labellingcontainers$getLabel(), x + leftPadding * 3, (y + 1) - fontHeight / 2, 0xFFFFFFFF);

            context.pop();
        }
    }
}