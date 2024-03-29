package infinituum.labellingcontainers.huds.utils;

import infinituum.labellingcontainers.utils.Taggable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import oshi.util.tuples.Pair;

public enum HudPositions {
    LEFT(
            (width, height, lineHeight, label, labelWidth) -> new Pair<>(2, height / 2 - 8),
            (width, height, lineHeight, label, labelWidth) -> new Pair<>(22, height / 2 - lineHeight / 2)
    ),
    CENTER_LEFT(
            (width, height, lineHeight, label, labelWidth) -> new Pair<>(width / 2 - 27, height / 2 - 8),
            (width, height, lineHeight, label, labelWidth) -> new Pair<>(width / 2 - labelWidth - 32, height / 2 - lineHeight / 2)
    ),
    CENTER_RIGHT(
            (width, height, lineHeight, label, labelWidth) -> new Pair<>(width / 2 + 10, height / 2 - 8),
            (width, height, lineHeight, label, labelWidth) -> new Pair<>(width / 2 + 30, height / 2 - lineHeight / 2)
    ),
    TOP_LEFT(
            (width, height, lineHeight, label, labelWidth) -> new Pair<>(2, 2),
            (width, height, lineHeight, label, labelWidth) -> new Pair<>(22, lineHeight / 2 + 2)
    ),
    TOP(
            (width, height, lineHeight, label, labelWidth) -> new Pair<>(width / 2 - (20 + labelWidth) / 2, 2),
            (width, height, lineHeight, label, labelWidth) -> new Pair<>(width / 2 + 20 - (20 + labelWidth) / 2, 2 + lineHeight / 2)
    );

    private final ScreenCoordsFunc labelCoordsFunc;
    private final ScreenCoordsFunc itemCoordsFunc;

    HudPositions(ScreenCoordsFunc itemFunc, ScreenCoordsFunc labelFunc) {
        this.itemCoordsFunc = itemFunc;
        this.labelCoordsFunc = labelFunc;
    }

    public static HudPositions getDefault() {
        return CENTER_RIGHT;
    }

    public static String toReadable(HudPositions position) {
        return switch (position) {
            case LEFT -> "left";
            case CENTER_LEFT -> "center-left";
            case CENTER_RIGHT -> "center-right";
            case TOP_LEFT -> "top-left";
            case TOP -> "top";
        };
    }

    public static HudPositions fromReadable(String readable) {
        return switch (readable) {
            case "left" -> HudPositions.LEFT;
            case "center-left" -> HudPositions.CENTER_LEFT;
            case "center-right" -> HudPositions.CENTER_RIGHT;
            case "top-left" -> HudPositions.TOP_LEFT;
            case "top" -> HudPositions.TOP;
            default -> getDefault();
        };
    }

    public Pair<Integer, Integer> computeLabelCoords(int width, int height, int lineHeight, Component label, int labelWidth) {
        return this.labelCoordsFunc.compute(width, height, lineHeight, label, labelWidth);
    }

    public Pair<Integer, Integer> computeItemCoords(int width, int height, int lineHeight, Component label, int labelWidth) {
        return this.itemCoordsFunc.compute(width, height, lineHeight, label, labelWidth);
    }

    @Environment(EnvType.CLIENT)
    public void render(Minecraft client, GuiGraphics graphics, Taggable taggable) {
        if (client.level == null) return;

        int width = client.getWindow().getGuiScaledWidth();
        int height = client.getWindow().getGuiScaledHeight();

        Component label = taggable.labellingcontainers$getLabel();
        Item displayItem = taggable.labellingcontainers$getDisplayItem();

        int labelWidth = client.font.width(label);

        Pair<Integer, Integer> itemCoords = this.computeItemCoords(width, height, client.font.lineHeight, label, labelWidth);
        Pair<Integer, Integer> labelCoords = this.computeLabelCoords(width, height, client.font.lineHeight, label, labelWidth);

        graphics.pose().pushPose();

        graphics.renderItem(new ItemStack(displayItem), itemCoords.getA(), itemCoords.getB());
        graphics.drawString(client.font, label, labelCoords.getA(), labelCoords.getB(), 0xFFFFFFFF);

        graphics.pose().popPose();
    }

    public interface ScreenCoordsFunc {
        Pair<Integer, Integer> compute(int width, int height, int lineHeight, Component label, int labelWidth);
    }
}
