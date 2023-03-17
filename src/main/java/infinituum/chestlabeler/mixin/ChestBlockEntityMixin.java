package infinituum.chestlabeler.mixin;

import infinituum.chestlabeler.utils.Labelable;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(ChestBlockEntity.class)
public class ChestBlockEntityMixin implements Labelable {
    @Override
    public Text getLabel() {
        return Text.literal("Chest").formatted(Formatting.ITALIC);
    }
}
