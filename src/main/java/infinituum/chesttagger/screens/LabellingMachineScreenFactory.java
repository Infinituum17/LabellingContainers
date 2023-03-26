package infinituum.chesttagger.screens;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

public class LabellingMachineScreenFactory implements NamedScreenHandlerFactory {
    @Override
    public Text getDisplayName() {
        return Text.translatable("item.chesttagger.labelling_machine.gui_display_name");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new LabellingMachineScreenHandler(syncId, playerInventory);
    }
}
