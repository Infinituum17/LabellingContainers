package infinituum.labellingcontainers.utils;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryHelper {
    public static void removeOneItemFromInventory(PlayerInventory inventory, Item item) {
        for(int slot = 0; slot < inventory.size(); slot++) {
            ItemStack stack = inventory.getStack(slot);

            if (!stack.isOf(item)) continue;

            stack.setCount(stack.getCount() - 1);
            inventory.setStack(slot, stack);

            return;
        }
    }
}