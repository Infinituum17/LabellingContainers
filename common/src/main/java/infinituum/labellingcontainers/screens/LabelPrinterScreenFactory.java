package infinituum.labellingcontainers.screens;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

import static infinituum.labellingcontainers.registration.ItemRegistration.LABEL_PRINTER;

public class LabelPrinterScreenFactory implements MenuProvider {
    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(LABEL_PRINTER.get().getDescriptionId() + ".gui_display_name");
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new LabelPrinterScreenHandler(syncId, playerInventory);
    }
}