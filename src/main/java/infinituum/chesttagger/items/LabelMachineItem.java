package infinituum.chesttagger.items;

import infinituum.chesttagger.screens.LabellingMachineScreenFactory;
import infinituum.chesttagger.utils.TaggableChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LabelMachineItem extends Item {
    public LabelMachineItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        ItemStack itemStack = context.getStack();

        if(block == Blocks.CHEST && world.getBlockEntity(pos) instanceof TaggableChest taggableChest) {
            NbtCompound labelNbt = itemStack.getSubNbt("Label");
            String label = (labelNbt != null) ? labelNbt.getString("text") : "";

            taggableChest.setLabel(Text.literal(label));

            NbtCompound displayItemNbt = itemStack.getSubNbt("Contents");
            Item displayItem = (displayItemNbt != null) ? ItemStack.fromNbt(displayItemNbt).getItem() : ItemStack.EMPTY.getItem();

            taggableChest.setDisplayItem(displayItem);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient()) {
            user.openHandledScreen(new LabellingMachineScreenFactory());
        }

        return super.use(world, user, hand);
    }
}
