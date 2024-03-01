package infinituum.labellingcontainers.forge.mixin.more_chests;

import games.twinhead.morechests.block.BasicChestBlock;
import infinituum.labellingcontainers.utils.ChestHelper;
import infinituum.labellingcontainers.utils.TaggableChest;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BasicChestBlock.class)
public class BasicChestBlockMixin extends Block {
    public BasicChestBlockMixin(Properties arg) {
        super(arg);
    }

    @Override
    public void setPlacedBy(Level world, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack itemStack) {
        if (world.isClientSide()) return;
        if (world.getBlockEntity(pos) == null) return;

        BlockEntity currentChestBlockEntity = world.getBlockEntity(pos);
        ChestBlockEntity connectedChestBlockEntity = ChestHelper.getConnectedChestBlockEntity(world, pos, state);

        if (connectedChestBlockEntity instanceof TaggableChest connectedChest && currentChestBlockEntity instanceof TaggableChest currentChest) {
            currentChest.labellingcontainers$setLabel(connectedChest.labellingcontainers$getLabel(), false);
            currentChest.labellingcontainers$setDisplayItem(connectedChest.labellingcontainers$getDisplayItem(), false);
        }
    }
}
