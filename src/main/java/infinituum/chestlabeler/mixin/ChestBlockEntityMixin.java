package infinituum.chestlabeler.mixin;

import infinituum.chestlabeler.utils.Labelable;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(ChestBlockEntity.class)
public class ChestBlockEntityMixin implements Labelable {
    @Nullable @Override
    public Item getLabelDisplayItem() {
        return null;
    }

    @Override
    public Text getLabel() {
        return Text.literal("Chest").formatted(Formatting.ITALIC);
    }
}
