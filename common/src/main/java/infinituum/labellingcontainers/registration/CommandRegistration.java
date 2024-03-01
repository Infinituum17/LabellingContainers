package infinituum.labellingcontainers.registration;

import dev.architectury.event.events.common.CommandRegistrationEvent;
import infinituum.labellingcontainers.utils.Taggable;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class CommandRegistration {
    public static void init() {
        CommandRegistrationEvent.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("setlabel")
                    .then(argument("location", Vec3Argument.vec3())
                            .then(literal("label")
                                    .then(argument("label", MessageArgument.message()).executes(context -> {
                                        BlockPos pos = Vec3Argument.getCoordinates(context, "location").getBlockPos(context.getSource());
                                        MutableComponent label = MessageArgument.getMessage(context, "label").copy();

                                        Level world = context.getSource().getLevel();

                                        BlockEntity be = world.getBlockEntity(pos);

                                        if (be instanceof Taggable labelable) {
                                            labelable.labellingcontainers$setLabel(label);
                                        }

                                        return 1;
                                    }))
                            )
                    )
            );

            dispatcher.register(literal("setlabel")
                    .then(argument("location", Vec3Argument.vec3())
                            .then(literal("item")
                                    .then(argument("display-item", ItemArgument.item(registryAccess)).executes(context -> {
                                        BlockPos pos = Vec3Argument.getCoordinates(context, "location").getBlockPos(context.getSource());
                                        Item item = ItemArgument.getItem(context, "display-item").getItem();

                                        Level world = context.getSource().getLevel();

                                        BlockEntity be = world.getBlockEntity(pos);

                                        if (be instanceof Taggable labelable) {
                                            labelable.labellingcontainers$setDisplayItem(item);
                                        }

                                        return 1;
                                    }))
                            )
                    )
            );
        });
    }
}
