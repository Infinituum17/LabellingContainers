package infinituum.labellingcontainers.registration;

import dev.architectury.event.events.common.CommandRegistrationEvent;
import infinituum.labellingcontainers.utils.Taggable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import static infinituum.labellingcontainers.LabellingContainersConfig.TAGGABLE_BLOCKS_CONFIG;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class CommandRegistration {
    public static void init() {
        CommandRegistrationEvent.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("setlabel")
                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                    .then(argument("location", Vec3Argument.vec3())
                            .then(literal("label")
                                    .then(argument("label", MessageArgument.message()).executes(context -> {
                                        BlockPos pos = Vec3Argument.getCoordinates(context, "location").getBlockPos(context.getSource());
                                        MutableComponent label = MessageArgument.getMessage(context, "label").copy();

                                        Level world = context.getSource().getLevel();

                                        BlockEntity be = world.getBlockEntity(pos);

                                        if (be instanceof Taggable labelable) {
                                            labelable.labellingcontainers$setLabel(label, true);
                                        }

                                        return 1;
                                    }))
                            )
                    )
            );

            dispatcher.register(literal("setlabel")
                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                    .then(argument("location", Vec3Argument.vec3())
                            .then(literal("item")
                                    .then(argument("display-item", ItemArgument.item(registryAccess)).executes(context -> {
                                        BlockPos pos = Vec3Argument.getCoordinates(context, "location").getBlockPos(context.getSource());
                                        Item item = ItemArgument.getItem(context, "display-item").getItem();

                                        Level world = context.getSource().getLevel();

                                        BlockEntity be = world.getBlockEntity(pos);

                                        if (be instanceof Taggable labelable) {
                                            labelable.labellingcontainers$setDisplayItem(item, true);
                                        }

                                        return 1;
                                    }))
                            )
                    )
            );

            dispatcher.register(literal("labelconfig")
                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                    .then(literal("add-hand")
                            .executes(context -> {
                                CommandSourceStack commandContext = context.getSource();

                                if (commandContext == null) return 0;

                                Player player = commandContext.getPlayer();

                                if (player == null) return 0;

                                Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
                                ResourceLocation resourceLocation = item.arch$registryName();

                                if (resourceLocation == null) return 0;
                                if (TAGGABLE_BLOCKS_CONFIG.get().hasId(resourceLocation.toString())) return 0;

                                TAGGABLE_BLOCKS_CONFIG.get().addId(resourceLocation.toString());
                                TAGGABLE_BLOCKS_CONFIG.writeCurrentToDisk();

                                player.sendSystemMessage(Component.translatable("command.labelconfig.addition.success", resourceLocation.toString()));

                                return 1;
                            })
                    )
            );

            dispatcher.register(literal("labelconfig")
                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                    .then(literal("remove-hand")
                            .executes(context -> {
                                CommandSourceStack commandContext = context.getSource();

                                if (commandContext == null) return 0;

                                Player player = commandContext.getPlayer();

                                if (player == null) return 0;

                                Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
                                ResourceLocation resourceLocation = item.arch$registryName();

                                if (resourceLocation == null) return 0;
                                if (!TAGGABLE_BLOCKS_CONFIG.get().hasId(resourceLocation.toString())) return 0;

                                TAGGABLE_BLOCKS_CONFIG.get().removeId(resourceLocation.toString());
                                TAGGABLE_BLOCKS_CONFIG.writeCurrentToDisk();

                                player.sendSystemMessage(Component.translatable("command.labelconfig.removal.success", resourceLocation.toString()));

                                return 1;
                            })
                    )
            );

            dispatcher.register(literal("labelconfig")
                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                    .then(literal("add-item")
                            .then(argument("item", ItemArgument.item(registryAccess))
                                    .executes(context -> {
                                        CommandSourceStack commandContext = context.getSource();
                                        Item item = ItemArgument.getItem(context, "item").getItem();

                                        if (commandContext == null) return 0;

                                        Player player = commandContext.getPlayer();

                                        if (player == null) return 0;

                                        ResourceLocation resourceLocation = item.arch$registryName();

                                        if (resourceLocation == null) return 0;
                                        if (TAGGABLE_BLOCKS_CONFIG.get().hasId(resourceLocation.toString())) return 0;

                                        TAGGABLE_BLOCKS_CONFIG.get().addId(resourceLocation.toString());
                                        TAGGABLE_BLOCKS_CONFIG.writeCurrentToDisk();

                                        player.sendSystemMessage(Component.translatable("command.labelconfig.addition.success", resourceLocation.toString()));

                                        return 1;
                                    })
                            )
                    )
            );

            dispatcher.register(literal("labelconfig")
                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                    .then(literal("remove-item")
                            .then(argument("item", ItemArgument.item(registryAccess))
                                    .executes(context -> {
                                        CommandSourceStack commandContext = context.getSource();
                                        Item item = ItemArgument.getItem(context, "item").getItem();

                                        if (commandContext == null) return 0;

                                        Player player = commandContext.getPlayer();

                                        if (player == null) return 0;

                                        ResourceLocation resourceLocation = item.arch$registryName();

                                        if (resourceLocation == null) return 0;
                                        if (!TAGGABLE_BLOCKS_CONFIG.get().hasId(resourceLocation.toString())) return 0;

                                        TAGGABLE_BLOCKS_CONFIG.get().removeId(resourceLocation.toString());
                                        TAGGABLE_BLOCKS_CONFIG.writeCurrentToDisk();

                                        player.sendSystemMessage(Component.translatable("command.labelconfig.removal.success", resourceLocation.toString()));

                                        return 1;
                                    })
                            )
                    )
            );
        });
    }
}
