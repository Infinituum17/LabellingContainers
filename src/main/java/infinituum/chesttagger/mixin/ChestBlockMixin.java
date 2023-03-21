package infinituum.chesttagger.mixin;

import infinituum.chesttagger.utils.ChestHelper;
import infinituum.chesttagger.utils.TaggableChest;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlock.class)
public class ChestBlockMixin {
    @Inject(method = "onPlaced", at = @At("HEAD"))
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        if(world.isClient()) return;
        if(world.getBlockEntity(pos) == null) return;

        BlockEntity currentChestBlockEntity = world.getBlockEntity(pos);
        ChestBlockEntity connectedChestBlockEntity = ChestHelper.getConnectedChestBlockEntity(world, pos, state);

        if(connectedChestBlockEntity instanceof TaggableChest connectedChest && currentChestBlockEntity instanceof TaggableChest currentChest) {
            currentChest.setLabel(connectedChest.getLabel(), false);
            currentChest.setDisplayItem(connectedChest.getDisplayItem(), false);
        }
    }
}
