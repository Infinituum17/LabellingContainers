package infinituum.labellingcontainers.huds.utils;

import infinituum.labellingcontainers.utils.Taggable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import oshi.util.tuples.Pair;

public enum HUDPositions {
    LEFT(
            (width, height, font, label) -> new Pair<>(2, height / 2 - 8),
            (width, height, font, label) -> new Pair<>(22, height / 2 - font.lineHeight / 2)
    ),
    CENTER_LEFT(
            (width, height, font, label) -> new Pair<>(width / 2 - 27, height / 2 - 8),
            (width, height, font, label) -> new Pair<>(width / 2 - font.width(label) - 32, height / 2 - font.lineHeight / 2)
    ),
    CENTER_RIGHT(
            (width, height, font, label) -> new Pair<>(width / 2 + 10, height / 2 - 8),
            (width, height, font, label) -> new Pair<>(width / 2 + 30, height / 2 - font.lineHeight / 2)
    ),
    TOP_LEFT(
            (width, height, font, label) -> new Pair<>(2, 2),
            (width, height, font, label) -> new Pair<>(22, font.lineHeight / 2 + 2)
    ),
    TOP(
            (width, height, font, label) -> new Pair<>(width / 2 - (20 + font.width(label)) / 2, 2),
            (width, height, font, label) -> new Pair<>(width / 2 + 20 - (20 + font.width(label)) / 2, 2 + font.lineHeight / 2)
    );

    private final ScreenCoordsFunc labelCoordsFunc;
    private final ScreenCoordsFunc itemCoordsFunc;
    HUDPositions(ScreenCoordsFunc itemFunc, ScreenCoordsFunc labelFunc) {
        this.itemCoordsFunc = itemFunc;
        this.labelCoordsFunc = labelFunc;
    }

    public static HUDPositions getDefault() {
        return CENTER_RIGHT;
    }

    public static String toReadable(HUDPositions position) {
        return switch (position) {
            case LEFT -> "left";
            case CENTER_LEFT -> "center-left";
            case CENTER_RIGHT -> "center-right";
            case TOP_LEFT -> "top-left";
            case TOP -> "top";
        };
    }

    public static HUDPositions fromReadable(String readable) {
        return switch (readable) {
            case "left" -> HUDPositions.LEFT;
            case "center-left" -> HUDPositions.CENTER_LEFT;
            case "center-right" -> HUDPositions.CENTER_RIGHT;
            case "top-left" -> HUDPositions.TOP_LEFT;
            case "top" -> HUDPositions.TOP;
            default -> getDefault();
        };
    }

    public Pair<Integer, Integer> computeLabelCoords(int width, int height, Font fontHeight, Component label) {
        return this.labelCoordsFunc.compute(width, height, fontHeight, label);
    }

    public Pair<Integer, Integer> computeItemCoords(int width, int height, Font fontHeight, Component label) {
        return this.itemCoordsFunc.compute(width, height, fontHeight, label);
    }

    public void render(Minecraft client, GuiGraphics graphics, Taggable taggable) {
        if (client.level == null) return;

        int width = client.getWindow().getGuiScaledWidth();
        int height = client.getWindow().getGuiScaledHeight();

        Component label = taggable.labellingcontainers$getLabel();
        Item displayItem = taggable.labellingcontainers$getDisplayItem();

        Pair<Integer, Integer> itemCoords = this.computeItemCoords(width, height, client.font, label);
        Pair<Integer, Integer> labelCoords = this.computeLabelCoords(width, height, client.font, label);

        graphics.pose().pushPose();

        graphics.renderItem(new ItemStack(displayItem), itemCoords.getA(), itemCoords.getB());
        graphics.drawString(client.font, label, labelCoords.getA(), labelCoords.getB(), 0xFFFFFFFF);

        graphics.pose().popPose();
    }

    public interface ScreenCoordsFunc {
        Pair<Integer, Integer> compute(int width, int height, Font fontHeight, Component label);
    }
}