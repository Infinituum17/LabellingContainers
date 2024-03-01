package infinituum.labellingcontainers.fabric.mixin.more_chests;

import games.twinhead.morechests.block.CustomChestBlock;
import infinituum.labellingcontainers.utils.ChestHelper;
import infinituum.labellingcontainers.utils.TaggableChest;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CustomChestBlock.class)
public class CustomChestBlockMixin {
    @Inject(method = "setPlacedBy", at = @At("HEAD"))
    public void onPlaced(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
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
