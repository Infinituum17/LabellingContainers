package infinituum.labellingcontainers.screens;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

import static infinituum.labellingcontainers.registration.ItemRegistration.LABEL_PRINTER;

public class LabelPrinterScreenFactory implements NamedScreenHandlerFactory {
    @Override
    public Text getDisplayName() {
        return Text.translatable(LABEL_PRINTER.getTranslationKey() + ".gui_display_name");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new LabelPrinterScreenHandler(syncId, playerInventory);
    }
}
