package infinituum.labellingcontainers.guis;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.architectury.networking.NetworkManager;
import infinituum.labellingcontainers.items.LabelPrinterItem;
import infinituum.labellingcontainers.network.Packets;
import infinituum.labellingcontainers.screens.LabelPrinterScreenHandler;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;


public class LabelPrinterGui extends AbstractContainerScreen<LabelPrinterScreenHandler> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(MOD_ID, "textures/gui/label_printer_gui.png");
    private final Player player;
    private EditBox labelField;

    public LabelPrinterGui(LabelPrinterScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        this.player = inventory.player;
    }

    private void setup() {
        int paddingLeft = 25;
        int paddingTop = 7;
        int x = width / 2 - paddingLeft;
        int y = (height / 2) - (imageHeight / 3) + paddingTop;
        this.labelField = new EditBox(this.font, x, y, 96, 16, Component.literal(""));

        this.labelField.setCanLoseFocus(false);
        this.labelField.setTextColor(-1);
        this.labelField.setTextColorUneditable(-1);
        this.labelField.setBordered(true);
        this.labelField.setMaxLength(50);

        String label = null;

        if(player != null) {
            ItemStack item = this.player.getInventory().getSelected();

            if (item.getItem() instanceof LabelPrinterItem) {
                label = LabelPrinterItem.getLabel(item);
            }
        }

        this.labelField.setValue(label);
        this.addWidget(this.labelField);
        this.setInitialFocus(this.labelField);
        this.labelField.setEditable(true);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        if (this.labelField == null) setup();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256 && this.minecraft != null && this.minecraft.player != null) {
            this.onClose();
        }

        return this.labelField.keyPressed(keyCode, scanCode, modifiers) ||
                this.labelField.canConsumeInput() ||
                super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() {
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());

        buffer.writeUtf(this.labelField.getValue());

        NetworkManager.sendToServer(Packets.C2S_LABEL_PRINTER_SAVE, buffer);

        super.onClose();
    }

    @Override
    public void resize(Minecraft client, int width, int height) {
        String string = this.labelField.getValue();
        this.init(client, width, height);
        this.setup();
        this.labelField.setValue(string);
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        context.blit(BACKGROUND, x, y, 0, 0, imageWidth, imageHeight);
    }

    protected void renderForeground(GuiGraphics context, int mouseX, int mouseY, float delta) {
        this.labelField.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        if (this.labelField == null) setup();
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        renderForeground(context, mouseX, mouseY, delta);
        renderTooltip(context, mouseX, mouseY);
    }
}