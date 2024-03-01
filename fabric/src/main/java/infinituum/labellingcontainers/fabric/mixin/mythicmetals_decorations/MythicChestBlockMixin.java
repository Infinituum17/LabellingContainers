/*package infinituum.labellingcontainers.fabric.mixin.mythicmetals_decorations;

import infinituum.labellingcontainers.utils.ChestHelper;
import infinituum.labellingcontainers.utils.TaggableChest;
import infinituum.labellingcontainers.utils.TaggableTooltip;
import nourl.mythicmetalsdecorations.blocks.chest.MythicChestBlock;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(MythicChestBlock.class)
public class MythicChestBlockMixin extends Block {
    public MythicChestBlockMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "onPlaced", at = @At("HEAD"))
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

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter world, List<Component> tooltip, TooltipFlag options) {
        super.appendHoverText(stack, world, tooltip, options);

        tooltip.add(TaggableTooltip.get());
    }
}*/