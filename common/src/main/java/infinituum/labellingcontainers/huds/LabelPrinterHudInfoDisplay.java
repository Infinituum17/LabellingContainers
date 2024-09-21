package infinituum.labellingcontainers.huds;

import dev.architectury.event.events.client.ClientGuiEvent.RenderHud;
import infinituum.labellingcontainers.items.LabelPrinterItem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class LabelPrinterHudInfoDisplay implements RenderHud {
    @Override
    public void renderHud(GuiGraphics graphics, DeltaTracker tickDelta) {
        Minecraft client = Minecraft.getInstance();
        if (client.screen != null || client.player == null) {
            return;
        }

        ItemStack itemInHand = client.player.getItemInHand(InteractionHand.MAIN_HAND);

        if (itemInHand.getItem() instanceof LabelPrinterItem) {
            int height = client.getWindow().getGuiScaledHeight();
            int fontHeight = client.font.lineHeight;
            int padding = 2;

            Component mode = Component
                    .translatable("item.labellingcontainers.label_printer.tooltip.mode")
                    .append(LabelPrinterItem.getMode(itemInHand).getDisplayable());

            graphics.pose().pushPose();

            graphics.drawString(client.font, mode, padding, height - fontHeight - padding, 0xFFFFFFFF);

            graphics.pose().popPose();
        }
    }
}
