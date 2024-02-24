package infinituum.labellingcontainers.forge.mixin.netherchested;

import fuzs.netherchested.world.level.block.NetherChestBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(NetherChestBlock.class)
public class NetherChestBlockMixin extends Block {
    public NetherChestBlockMixin(Properties arg) {
        super(arg);
    }

    @Inject(method = "appendHoverText", at = @At("TAIL"))
    public void appendTooltip(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag, CallbackInfo ci) {
        MutableComponent text = Component.literal("ⓘ ").withStyle(ChatFormatting.BLUE);
        text.append(Component.translatable("block.labelable").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        tooltip.add(text);
    }
}
