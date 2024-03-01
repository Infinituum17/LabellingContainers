package infinituum.labellingcontainers.screens;

import infinituum.labellingcontainers.registration.ScreenRegistration;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class LabelPrinterScreenHandler extends AbstractContainerMenu {
    private final Container inventory;

    public LabelPrinterScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(1));
    }

    public LabelPrinterScreenHandler(int syncId, Inventory playerInventory, Container inventory) {
        super(ScreenRegistration.LABEL_PRINTER_SCREEN_HANDLER.get(), syncId);

        checkContainerSize(inventory, 1);

        playerInventory.getSelected();
        CompoundTag nbt = playerInventory.getSelected().getTagElement("Contents");

        if (nbt != null) {
            ItemStack items = ItemStack.of(nbt);

            if (items != ItemStack.EMPTY) {
                inventory.setItem(0, items);
            }
        }

        this.inventory = inventory;

        inventory.startOpen(playerInventory.player);

        this.addSlot(new Slot(inventory, 0, 20, 35));

        //The player inventory
        for (int m = 0; m < 3; ++m) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (int m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    @Override
    public void clicked(int slotIndex, int button, ClickType actionType, Player player) {
        boolean inPlayerInventory = slotIndex >= this.inventory.getContainerSize();

        if (slotIndex < 0) {
            super.clicked(slotIndex, button, actionType, player);
            return;
        }

        Slot slot = this.slots.get(slotIndex);

        switch (actionType) {
            case PICKUP, PICKUP_ALL -> {
                if (!inPlayerInventory) {
                    this.setItem(slotIndex, this.incrementStateId(), (this.getCarried() != ItemStack.EMPTY) ? new ItemStack(this.getCarried().getItem()) : ItemStack.EMPTY);
                } else {
                    ItemStack itemInHand = player.getMainHandItem();

                    if (slot.hasItem() && ItemStack.matches(itemInHand, slot.getItem())) {
                        return;
                    }
                }
            }
            case QUICK_MOVE -> {
                if (!inPlayerInventory) {
                    this.setItem(slotIndex, this.incrementStateId(), ItemStack.EMPTY);
                } else {
                    if (slot.hasItem())
                        this.setItem(0, this.incrementStateId(), new ItemStack(slot.getItem().getItem()));
                }
            }
        }

        if (inPlayerInventory) super.clicked(slotIndex, button, actionType, player);
    }

    @Override
    public void removed(Player player) {
        player.getMainHandItem();
        if (player.getMainHandItem() != ItemStack.EMPTY) {
            ItemStack itemStack = this.inventory.getItem(0);
            CompoundTag nbt = new CompoundTag();

            itemStack.save(nbt);

            player.getMainHandItem().addTagElement("Contents", nbt);
        }


        super.removed(player);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }
}