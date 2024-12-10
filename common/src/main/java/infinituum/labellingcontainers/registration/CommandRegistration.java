package infinituum.labellingcontainers.registration;

import com.mojang.brigadier.context.CommandContext;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.networking.NetworkManager;
import infinituum.fastconfigapi.FastConfigs;
import infinituum.fastconfigapi.api.FastConfigFile;
import infinituum.labellingcontainers.config.CompatibleContainers;
import infinituum.labellingcontainers.huds.utils.HudPositions;
import infinituum.labellingcontainers.network.packets.s2c.*;
import infinituum.labellingcontainers.utils.Taggable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

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
                                        CommandSourceStack source = context.getSource();
                                        BlockPos pos = Vec3Argument.getCoordinates(context, "location").getBlockPos(source);
                                        MutableComponent label = MessageArgument.getMessage(context, "label").copy();
                                        Level level = source.getLevel();
                                        BlockEntity be = level.getBlockEntity(pos);

                                        if (be instanceof Taggable labelable) {
                                            labelable.labellingcontainers$setLabel(label, true);
                                            source.sendSystemMessage(Component.translatable("commands.setlabel.label.success", label, pos.getX(), pos.getY(), pos.getZ()));
                                            return 1;
                                        }

                                        BlockState state = level.getBlockState(pos);
                                        ResourceLocation itemId = state.getBlock().asItem().arch$registryName();

                                        if (state.getBlock().equals(Blocks.AIR)) {
                                            source.sendSystemMessage(Component.translatable("commands.setlabel.label.invalid", label, pos.getX(), pos.getY(), pos.getZ()));
                                            return 1;
                                        }

                                        source.sendSystemMessage(Component.translatable("commands.setlabel.label.failed", itemId, pos.getX(), pos.getY(), pos.getZ(), itemId));
                                        return 0;
                                    }))
                            )
                    )
            );

            dispatcher.register(literal("setlabel")
                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                    .then(argument("location", Vec3Argument.vec3())
                            .then(literal("item")
                                    .then(argument("display-item", ItemArgument.item(registryAccess)).executes(context -> {
                                        CommandSourceStack source = context.getSource();
                                        BlockPos pos = Vec3Argument.getCoordinates(context, "location").getBlockPos(source);
                                        Item item = ItemArgument.getItem(context, "display-item").getItem();
                                        Level level = source.getLevel();
                                        BlockEntity be = level.getBlockEntity(pos);

                                        if (be instanceof Taggable labelable) {
                                            labelable.labellingcontainers$setDisplayItem(item, true);
                                            source.sendSystemMessage(Component.translatable("commands.setlabel.displayitem.success", item, pos.getX(), pos.getY(), pos.getZ()));
                                            return 1;
                                        }

                                        BlockState state = level.getBlockState(pos);

                                        if (state.getBlock().equals(Blocks.AIR)) {
                                            source.sendSystemMessage(Component.translatable("commands.setlabel.displayitem.invalid", item, pos.getX(), pos.getY(), pos.getZ()));
                                            return 1;
                                        }

                                        source.sendSystemMessage(Component.translatable("commands.setlabel.displayitem.failed", item, pos.getX(), pos.getY(), pos.getZ(), item));
                                        return 0;
                                    }))
                            )
                    )
            );

            dispatcher.register(literal("labelconfig")
                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                    .then(literal("add-hand")
                            .requires(CommandSourceStack::isPlayer)
                            .executes(context -> {
                                CommandSourceStack source = context.getSource();

                                if (source.isPlayer()) {
                                    ServerPlayer player = source.getPlayer();

                                    if (player == null) {
                                        source.sendSystemMessage(Component.translatable("commands.labelconfig.addhand.player.invalid"));
                                        return 0;
                                    }

                                    Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
                                    ResourceLocation resourceLocation = item.arch$registryName();

                                    if (resourceLocation == null) {
                                        source.sendSystemMessage(Component.translatable("commands.labelconfig.addhand.resource.invalid", item));
                                        return 0;
                                    }

                                    return addId(context, resourceLocation);
                                }

                                source.sendSystemMessage(Component.translatable("commands.labelconfig.addhand.context.failure"));
                                return 0;
                            })
                    )
            );

            dispatcher.register(literal("labelconfig")
                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                    .then(literal("remove-hand")
                            .requires(CommandSourceStack::isPlayer)
                            .executes(context -> {
                                CommandSourceStack source = context.getSource();

                                if (source.isPlayer()) {
                                    ServerPlayer player = source.getPlayer();

                                    if (player == null) {
                                        source.sendSystemMessage(Component.translatable("commands.labelconfig.removehand.player.invalid"));
                                        return 0;
                                    }

                                    Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
                                    ResourceLocation resourceLocation = item.arch$registryName();

                                    if (resourceLocation == null) {
                                        source.sendSystemMessage(Component.translatable("commands.labelconfig.removehand.resource.invalid", item));
                                        return 0;
                                    }

                                    return removeId(context, resourceLocation);
                                }

                                source.sendSystemMessage(Component.translatable("commands.labelconfig.removehand.context.failure"));
                                return 0;
                            })
                    )
            );

            dispatcher.register(literal("labelconfig")
                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                    .then(literal("add-item")
                            .then(argument("item", ItemArgument.item(registryAccess))
                                    .executes(context -> {
                                        CommandSourceStack source = context.getSource();
                                        Item item = ItemArgument.getItem(context, "item").getItem();
                                        ResourceLocation resourceLocation = item.arch$registryName();

                                        if (resourceLocation == null) {
                                            source.sendSystemMessage(Component.translatable("commands.labelconfig.additem.resource.invalid", item));
                                            return 0;
                                        }

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
                                        CommandSourceStack source = context.getSource();
                                        Item item = ItemArgument.getItem(context, "item").getItem();
                                        ResourceLocation resourceLocation = item.arch$registryName();

                                        if (resourceLocation == null) {
                                            source.sendSystemMessage(Component.translatable("commands.labelconfig.removeitem.resource.invalid", item));
                                            return 0;
                                        }

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
                                        String tag = MessageArgument.getMessage(context, "tag").getString();

                                        return addTag(context, tag);
                                    })
                            )
                    )
            );

            dispatcher.register(literal("labelconfig")
                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                    .then(literal("remove-tag")
                            .then(argument("tag", MessageArgument.message())
                                    .executes(context -> {
                                        String tag = MessageArgument.getMessage(context, "tag").getString();

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

    private static int addId(CommandContext<CommandSourceStack> context, ResourceLocation resourceLocation) {
        CommandSourceStack source = context.getSource();
        FastConfigFile<CompatibleContainers> configFile = FastConfigs.getFile(CompatibleContainers.class);
        CompatibleContainers config = configFile.getInstance();

        if (config.has(resourceLocation.toString())) {
            source.sendSystemMessage(Component.translatable("commands.labelconfig.add.id.no_change", resourceLocation.toString()));
            return 0;
        }

        config.addId(resourceLocation.toString());
        configFile.save();

        if (source.isPlayer() && source.getPlayer() != null) {
            NetworkManager.sendToPlayer(source.getPlayer(), new AddIdConfigPacket(resourceLocation.toString()));
        } else {
            NetworkManager.sendToPlayers(source.getServer().getPlayerList().getPlayers(), new AddIdConfigPacket(resourceLocation.toString()));
        }

        source.sendSystemMessage(Component.translatable("commands.labelconfig.add.id.success", resourceLocation.toString()));

        return 1;
    }

    private static int removeId(CommandContext<CommandSourceStack> context, ResourceLocation resourceLocation) {
        CommandSourceStack source = context.getSource();
        FastConfigFile<CompatibleContainers> configFile = FastConfigs.getFile(CompatibleContainers.class);
        CompatibleContainers config = configFile.getInstance();

        if (!config.has(resourceLocation.toString())) {
            source.sendSystemMessage(Component.translatable("commands.labelconfig.remove.id.no_change", resourceLocation.toString()));
            return 0;
        }

        config.removeId(resourceLocation.toString());
        configFile.save();

        if (source.isPlayer() && source.getPlayer() != null) {
            NetworkManager.sendToPlayer(source.getPlayer(), new RemoveIdConfigPacket(resourceLocation.toString()));
        } else {
            NetworkManager.sendToPlayers(source.getServer().getPlayerList().getPlayers(), new RemoveIdConfigPacket(resourceLocation.toString()));
        }

        source.sendSystemMessage(Component.translatable("commands.labelconfig.remove.id.success", resourceLocation.toString()));

        return 1;
    }

    private static int addTag(CommandContext<CommandSourceStack> context, String tag) {
        CommandSourceStack source = context.getSource();
        FastConfigFile<CompatibleContainers> configFile = FastConfigs.getFile(CompatibleContainers.class);
        CompatibleContainers config = configFile.getInstance();

        if (config.has(tag)) {
            source.sendSystemMessage(Component.translatable("commands.labelconfig.add.tag.no_change", tag));
            return 0;
        }

        config.addTag(tag);
        configFile.save();

        if (source.isPlayer() && source.getPlayer() != null) {
            NetworkManager.sendToPlayer(source.getPlayer(), new AddTagConfigPacket(tag));
        } else {
            NetworkManager.sendToPlayers(source.getServer().getPlayerList().getPlayers(), new AddTagConfigPacket(tag));
        }

        source.sendSystemMessage(Component.translatable("commands.labelconfig.add.tag.success", tag));

        return 1;
    }

    private static int removeTag(CommandContext<CommandSourceStack> context, String tag) {
        CommandSourceStack source = context.getSource();
        FastConfigFile<CompatibleContainers> configFile = FastConfigs.getFile(CompatibleContainers.class);
        CompatibleContainers config = configFile.getInstance();

        if (!config.has(tag)) {
            source.sendSystemMessage(Component.translatable("commands.labelconfig.remove.tag.no_change", tag));
            return 0;
        }

        config.removeTag(tag);
        configFile.save();

        if (source.isPlayer() && source.getPlayer() != null) {
            NetworkManager.sendToPlayer(source.getPlayer(), new RemoveTagConfigPacket(tag));
        } else {
            NetworkManager.sendToPlayers(source.getServer().getPlayerList().getPlayers(), new RemoveTagConfigPacket(tag));
        }

        source.sendSystemMessage(Component.translatable("commands.labelconfig.remove.tag.success", tag));

        return 1;
    }

    private static int setLabelPosition(CommandContext<CommandSourceStack> context, HudPositions position) {
        CommandSourceStack source = context.getSource();

        if (!source.isPlayer() || source.getPlayer() == null) {
            source.sendSystemMessage(Component.translatable("commands.labelposition.set.player.invalid"));
            return 0;
        }

        ServerPlayer player = source.getPlayer();

        NetworkManager.sendToPlayer(player, new UpdatePreferencesConfigPacket(HudPositions.toReadable(position)));
        source.sendSystemMessage(Component.translatable("commands.labelposition.set.success", HudPositions.toReadable(position)));

        return 1;
    }
}
