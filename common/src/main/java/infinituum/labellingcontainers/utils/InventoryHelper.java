package infinituum.labellingcontainers.utils;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class InventoryHelper {
    public static void removeOneItemFromInventory(Inventory inventory, Item item) {
        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            ItemStack stack = inventory.getItem(slot);

            if (!stack.is(item)) {
                continue;
            }

            stack.setCount(stack.getCount() - 1);
            inventory.setItem(slot, stack);

            return;
        }
    }
}