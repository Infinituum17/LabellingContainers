package infinituum.chesttagger.items;

import infinituum.chesttagger.screens.LabelPrinterScreenFactory;
import infinituum.chesttagger.utils.TaggableChest;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.item.Items.AIR;

public class LabelPrinterItem extends Item {
    public LabelPrinterItem(Settings settings) {
        super(settings);
    }

    private String getLabel(ItemStack itemStack) {
        NbtCompound labelNbt = itemStack.getSubNbt("Label");
        return (labelNbt != null) ? labelNbt.getString("text") : "";
    }

    private Item getDisplayItem(ItemStack itemStack) {
        NbtCompound displayItemNbt = itemStack.getSubNbt("Contents");
        return (displayItemNbt != null) ? ItemStack.fromNbt(displayItemNbt).getItem() : ItemStack.EMPTY.getItem();
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        ItemStack itemStack = context.getStack();

        if(world.getBlockEntity(pos) instanceof TaggableChest taggableChest) {
            taggableChest.setLabel(Text.literal(getLabel(itemStack)));
            taggableChest.setDisplayItem(getDisplayItem(itemStack));

            return ActionResult.SUCCESS;
        }

        return super.useOnBlock(context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient()) {
            user.openHandledScreen(new LabelPrinterScreenFactory());
        }

        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        String currentLabel = getLabel(stack);
        Item currentDisplayItem = getDisplayItem(stack);

        Text textBuffer;

        if(currentLabel.equals("")) {
            textBuffer = Text.translatable(this.getTranslationKey() + ".tooltip.label")
                    .formatted(Formatting.GRAY)
                    .append(Text.translatable(this.getTranslationKey() + ".tooltip.none")
                            .formatted(Formatting.DARK_RED));
        } else {
            textBuffer = Text.translatable(this.getTranslationKey() + ".tooltip.label")
                    .formatted(Formatting.GRAY)
                    .append(Text.literal("\"" + currentLabel + "\"")
                            .formatted(Formatting.GOLD));
        }

        tooltip.add(textBuffer);

        if(currentDisplayItem.equals(AIR)) {
            textBuffer = Text.translatable(this.getTranslationKey() + ".tooltip.display_item")
                    .formatted(Formatting.GRAY)
                    .append(Text.translatable(this.getTranslationKey() + ".tooltip.none")
                            .formatted(Formatting.DARK_RED));
        } else {
            textBuffer = Text.translatable(this.getTranslationKey() + ".tooltip.display_item")
                    .formatted(Formatting.GRAY)
                    .append(currentDisplayItem.getName().copy()
                            .formatted(Formatting.AQUA));
        }

        tooltip.add(textBuffer);

        super.appendTooltip(stack, world, tooltip, context);
    }
}
