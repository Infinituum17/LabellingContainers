package infinituum.labellingcontainers.guis;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.architectury.networking.NetworkManager;
import infinituum.labellingcontainers.network.Packets;
import infinituum.labellingcontainers.screens.LabelPrinterScreenHandler;
import io.netty.buffer.Unpooled;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;


public class LabelPrinterGui extends HandledScreen<LabelPrinterScreenHandler> {
    private static final Identifier BACKGROUND = new Identifier(MOD_ID, "textures/gui/label_printer_gui.png");
    private TextFieldWidget labelField;
    private final PlayerEntity player;

    public LabelPrinterGui(LabelPrinterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.player = inventory.player;
    }

    private void setup() {
        int paddingLeft = 25;
        int paddingTop = 7;
        int x = width / 2 - paddingLeft;
        int y = (height / 2) - (backgroundHeight / 3) + paddingTop;
        this.labelField = new TextFieldWidget(this.textRenderer, x, y, 96, 16, Text.literal(""));

        this.labelField.setFocusUnlocked(false);
        this.labelField.setEditableColor(-1);
        this.labelField.setUneditableColor(-1);
        this.labelField.setDrawsBackground(true);
        this.labelField.setMaxLength(50);

        NbtCompound nbt = null;

        if(player != null) {
            nbt = this.player.getInventory().getMainHandStack().getSubNbt("Label");
        }

        this.labelField.setText((nbt != null) ? nbt.getString("text") : "");
        this.addSelectableChild(this.labelField);
        this.setInitialFocus(this.labelField);
        this.labelField.setEditable(true);
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        if(this.labelField == null) setup();
        this.labelField.tick();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256 && this.client != null && this.client.player != null) {
            this.close();
        }

        return this.labelField.keyPressed(keyCode, scanCode, modifiers) ||
                this.labelField.isActive() ||
                super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void close() {
        PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());

        buffer.writeString(this.labelField.getText());

        NetworkManager.sendToServer(Packets.LABEL_UPDATE_PACKET_ID, buffer);

        super.close();
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        String string = this.labelField.getText();
        this.init(client, width, height);
        this.setup();
        this.labelField.setText(string);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    protected void renderForeground(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.labelField.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if(this.labelField == null) setup();
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        renderForeground(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}