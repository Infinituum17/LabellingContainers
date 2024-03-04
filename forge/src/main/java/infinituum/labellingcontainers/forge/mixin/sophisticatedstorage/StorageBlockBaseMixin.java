package infinituum.labellingcontainers.forge.mixin.sophisticatedstorage;

import infinituum.labellingcontainers.utils.TaggableTooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.p3pp3rf1y.sophisticatedstorage.block.StorageBlockBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(StorageBlockBase.class)
public class StorageBlockBaseMixin extends Block {

    public StorageBlockBaseMixin(Properties arg) {
        super(arg);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack arg, @Nullable BlockGetter arg2, @NotNull List<Component> list, @NotNull TooltipFlag arg3) {
        super.appendHoverText(arg, arg2, list, arg3);

        list.add(TaggableTooltip.get());
    }
}
