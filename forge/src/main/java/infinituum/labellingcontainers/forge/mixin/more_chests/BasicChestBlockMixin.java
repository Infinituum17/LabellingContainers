package infinituum.labellingcontainers.forge.mixin.more_chests;

import games.twinhead.morechests.block.BasicChestBlock;
import infinituum.labellingcontainers.utils.ChestHelper;
import infinituum.labellingcontainers.utils.TaggableChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BasicChestBlock.class)
public class BasicChestBlockMixin extends Block {
    public BasicChestBlockMixin(Settings arg) {
        super(arg);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.isClient()) return;
        if (world.getBlockEntity(pos) == null) return;

        BlockEntity currentChestBlockEntity = world.getBlockEntity(pos);
        ChestBlockEntity connectedChestBlockEntity = ChestHelper.getConnectedChestBlockEntity(world, pos, state);

        if (connectedChestBlockEntity instanceof TaggableChest connectedChest && currentChestBlockEntity instanceof TaggableChest currentChest) {
            currentChest.labellingcontainers$setLabel(connectedChest.labellingcontainers$getLabel(), false);
            currentChest.labellingcontainers$setDisplayItem(connectedChest.labellingcontainers$getDisplayItem(), false);
        }
    }
}
