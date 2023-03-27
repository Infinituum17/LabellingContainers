package infinituum.chesttagger.screens;

import infinituum.chesttagger.registration.ScreenRegistration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

public class LabelPrinterScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    public LabelPrinterScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(1));
    }

    public LabelPrinterScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ScreenRegistration.LABEL_PRINTER_SCREEN_HANDLER, syncId);

        checkSize(inventory, 1);

        if(playerInventory.getMainHandStack() != null) {
            NbtCompound nbt = playerInventory.getMainHandStack().getSubNbt("Contents");

            if(nbt != null) {
                ItemStack items = ItemStack.fromNbt(nbt);

                if(items != ItemStack.EMPTY) {
                    inventory.setStack(0, items);
                }
            }
        }

        this.inventory = inventory;

        inventory.onOpen(playerInventory.player);

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
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        boolean inPlayerInventory = slotIndex >= this.inventory.size();

        if(slotIndex < 0) {
            super.onSlotClick(slotIndex, button, actionType, player);
            return;
        }

        Slot slot = this.slots.get(slotIndex);

        switch (actionType) {
            case PICKUP, PICKUP_ALL -> {
                if(!inPlayerInventory) {
                    this.setStackInSlot(slotIndex, this.nextRevision(), (this.getCursorStack() != ItemStack.EMPTY) ? new ItemStack(this.getCursorStack().getItem()) : ItemStack.EMPTY);
                } else {
                    ItemStack itemInHand = player.getMainHandStack();

                    if(slot.hasStack() && ItemStack.areEqual(itemInHand, slot.getStack())) {
                        return;
                    }
                }
            }
            case QUICK_MOVE -> {
                if(!inPlayerInventory) {
                    this.setStackInSlot(slotIndex, this.nextRevision(), ItemStack.EMPTY);
                } else {
                    if(slot.hasStack()) this.setStackInSlot(0, this.nextRevision(), new ItemStack(slot.getStack().getItem()));
                }
            }
        }

        if(inPlayerInventory) super.onSlotClick(slotIndex, button, actionType, player);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        if(player.getMainHandStack() != null && player.getMainHandStack() != ItemStack.EMPTY) {
            ItemStack itemStack = this.inventory.getStack(0);
            NbtCompound nbt = new NbtCompound();

            itemStack.writeNbt(nbt);

            player.getMainHandStack().setSubNbt("Contents", nbt);
        }


        super.onClosed(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}