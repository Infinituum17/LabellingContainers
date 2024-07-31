package infinituum.labellingcontainers.registration;

import com.mojang.brigadier.context.CommandContext;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.networking.NetworkManager;
import infinituum.labellingcontainers.huds.utils.HudPositions;
import infinituum.labellingcontainers.network.Packets;
import infinituum.labellingcontainers.utils.Taggable;
import io.netty.buffer.Unpooled;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import static infinituum.labellingcontainers.LabellingContainersConfig.TAGGABLE_BLOCKS_CONFIG;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public final class CommandRegistration {
    public static void init() {
        CommandRegistrationEvent.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("setlabel")
                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                    .then(argument("location", Vec3Argument.vec3())
                            .then(literal("label")
                                    .then(argument("label", MessageArgument.message()).executes(context -> {
                                        BlockPos pos = Vec3Argument.getCoordinates(context, "location").getBlockPos(context.getSource());
                                        MutableComponent label = MessageArgument.getMessage(context, "label").copy();

                                        Level level = context.getSource().getLevel();

                                        BlockEntity be = level.getBlockEntity(pos);

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

                                        Level level = context.getSource().getLevel();

                                        BlockEntity be = level.getBlockEntity(pos);

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
                            .requires(CommandSourceStack::isPlayer)
                            .executes(context -> {
                                CommandSourceStack commandContext = context.getSource();

                                if (commandContext == null) {
                                    return 0;
                                }

                                ServerPlayer player = commandContext.getPlayer();

                                if (player == null) {
                                    return 0;
                                }

                                Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
                                ResourceLocation resourceLocation = item.arch$registryName();

                                return addId(context, resourceLocation);
                            })
                    )
            );

            dispatcher.register(literal("labelconfig")
                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                    .then(literal("remove-hand")
                            .requires(CommandSourceStack::isPlayer)
                            .executes(context -> {
                                CommandSourceStack commandContext = context.getSource();

                                if (commandContext == null) {
                                    return 0;
                                }

                                ServerPlayer player = commandContext.getPlayer();

                                if (player == null) {
                                    return 0;
                                }

                                Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
                                ResourceLocation resourceLocation = item.arch$registryName();

                                return removeId(context, resourceLocation);
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

                                        if (commandContext == null) {
                                            return 0;
                                        }

                                        ServerPlayer player = commandContext.getPlayer();

                                        if (player == null) {
                                            return 0;
                                        }

                                        ResourceLocation resourceLocation = item.arch$registryName();

                                        return addId(context, resourceLocation);
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

                                        if (commandContext == null) {
                                            return 0;
                                        }

                                        ServerPlayer player = commandContext.getPlayer();

                                        if (player == null) {
                                            return 0;
                                        }

                                        ResourceLocation resourceLocation = item.arch$registryName();

                                        return removeId(context, resourceLocation);
                                    })
                            )
                    )
            );

            dispatcher.register(literal("labelconfig")
                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                    .then(literal("add-tag")
                            .then(argument("tag", MessageArgument.message())
                                    .executes(context -> {
                                        CommandSourceStack commandContext = context.getSource();
                                        String tag = MessageArgument.getMessage(context, "tag").getString();

                                        if (commandContext == null) {
                                            return 0;
                                        }

                                        ServerPlayer player = commandContext.getPlayer();

                                        if (player == null) {
                                            return 0;
                                        }

                                        return addTag(context, tag);
                                    })
                            )
                    )
            );

            dispatcher.register(literal("labelconfig")
                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                    .then(literal("remove-tag")
                            .then(argument("item", MessageArgument.message())
                                    .executes(context -> {
                                        CommandSourceStack commandContext = context.getSource();
                                        String tag = MessageArgument.getMessage(context, "item").getString();

                                        if (commandContext == null) {
                                            return 0;
                                        }

                                        ServerPlayer player = commandContext.getPlayer();

                                        if (player == null) {
                                            return 0;
                                        }

                                        return removeTag(context, tag);
                                    })
                            )
                    )
            );

            dispatcher.register(literal("labelposition")
                    .requires(CommandSourceStack::isPlayer)
                    .then(literal("top")
                            .executes(context -> setLabelPosition(context, HudPositions.TOP))));

            dispatcher.register(literal("labelposition")
                    .requires(CommandSourceStack::isPlayer)
                    .then(literal("top-left")
                            .executes(context -> setLabelPosition(context, HudPositions.TOP_LEFT))));

            dispatcher.register(literal("labelposition")
                    .requires(CommandSourceStack::isPlayer)
                    .then(literal("center-left")
                            .executes(context -> setLabelPosition(context, HudPositions.CENTER_LEFT))));

            dispatcher.register(literal("labelposition")
                    .requires(CommandSourceStack::isPlayer)
                    .then(literal("center-right")
                            .executes(context -> setLabelPosition(context, HudPositions.CENTER_RIGHT))));

            dispatcher.register(literal("labelposition")
                    .requires(CommandSourceStack::isPlayer)
                    .then(literal("left")
                            .executes(context -> setLabelPosition(context, HudPositions.LEFT))));
        });
    }

    private static int setLabelPosition(CommandContext<CommandSourceStack> context, HudPositions position) {
        CommandSourceStack sourceStack = context.getSource();
        ServerPlayer player = sourceStack.getPlayer();

        if (player == null) {
            return 0;
        }

        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());

        buffer.writeUtf(HudPositions.toReadable(position));

        NetworkManager.sendToPlayer(player, Packets.S2C_PREFERENCES_CONFIG_UPDATE, buffer);

        return 1;
    }

    private static int addId(CommandContext<CommandSourceStack> context, ResourceLocation resourceLocation) {
        CommandSourceStack commandContext = context.getSource();

        if (commandContext == null) {
            return 0;
        }

        ServerPlayer player = commandContext.getPlayer();

        if (player == null || resourceLocation == null) {
            return 0;
        }
        if (TAGGABLE_BLOCKS_CONFIG.getConfig().has(resourceLocation.toString())) {
            return 0;
        }

        TAGGABLE_BLOCKS_CONFIG.getConfig().addId(resourceLocation.toString());
        TAGGABLE_BLOCKS_CONFIG.saveCurrent();

        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());

        buffer.writeUtf(resourceLocation.toString());

        NetworkManager.sendToPlayer(player, Packets.S2C_ADD_ID_TAGGABLE_BLOCKS_CONFIG, buffer);

        player.sendSystemMessage(Component.translatable("command.labelconfig.addition.success", resourceLocation.toString()));

        return 1;
    }

    private static int removeId(CommandContext<CommandSourceStack> context, ResourceLocation resourceLocation) {
        CommandSourceStack commandContext = context.getSource();

        if (commandContext == null) {
            return 0;
        }

        ServerPlayer player = commandContext.getPlayer();

        if (player == null || resourceLocation == null) {
            return 0;
        }
        if (!TAGGABLE_BLOCKS_CONFIG.getConfig().has(resourceLocation.toString())) {
            return 0;
        }

        TAGGABLE_BLOCKS_CONFIG.getConfig().removeId(resourceLocation.toString());
        TAGGABLE_BLOCKS_CONFIG.saveCurrent();

        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());

        buffer.writeUtf(resourceLocation.toString());

        NetworkManager.sendToPlayer(player, Packets.S2C_REMOVE_ID_TAGGABLE_BLOCKS_CONFIG, buffer);

        player.sendSystemMessage(Component.translatable("command.labelconfig.removal.success", resourceLocation.toString()));

        return 1;
    }

    private static int addTag(CommandContext<CommandSourceStack> context, String tag) {
        CommandSourceStack commandContext = context.getSource();

        if (commandContext == null) {
            return 0;
        }

        ServerPlayer player = commandContext.getPlayer();

        if (player == null || tag == null) {
            return 0;
        }
        if (TAGGABLE_BLOCKS_CONFIG.getConfig().has(tag)) {
            return 0;
        }

        TAGGABLE_BLOCKS_CONFIG.getConfig().addTag(tag);
        TAGGABLE_BLOCKS_CONFIG.saveCurrent();

        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());

        buffer.writeUtf(tag);

        NetworkManager.sendToPlayer(player, Packets.S2C_ADD_TAG_TAGGABLE_BLOCKS_CONFIG, buffer);

        player.sendSystemMessage(Component.translatable("command.labelconfig.addition.success", tag));

        return 1;
    }

    private static int removeTag(CommandContext<CommandSourceStack> context, String tag) {
        CommandSourceStack commandContext = context.getSource();

        if (commandContext == null) {
            return 0;
        }

        ServerPlayer player = commandContext.getPlayer();

        if (player == null || tag == null) {
            return 0;
        }
        if (!TAGGABLE_BLOCKS_CONFIG.getConfig().has(tag)) {
            return 0;
        }

        TAGGABLE_BLOCKS_CONFIG.getConfig().removeTag(tag);
        TAGGABLE_BLOCKS_CONFIG.saveCurrent();

        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());

        buffer.writeUtf(tag);

        NetworkManager.sendToPlayer(player, Packets.S2C_REMOVE_TAG_TAGGABLE_BLOCKS_CONFIG, buffer);

        player.sendSystemMessage(Component.translatable("command.labelconfig.removal.success", tag));

        return 1;
    }
}
