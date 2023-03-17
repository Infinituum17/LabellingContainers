package infinituum.chestlabeler.commands;

import infinituum.chestlabeler.utils.Labelable;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CommandRegistration {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("setlabel")
                    .then(argument("location", Vec3ArgumentType.vec3())
                            .then(literal("label")
                                    .then(argument("label", MessageArgumentType.message()).executes(context -> {
                                        BlockPos pos = Vec3ArgumentType.getPosArgument(context, "location").toAbsoluteBlockPos(context.getSource());
                                        Text label = MessageArgumentType.getMessage(context, "label");

                                        World world = context.getSource().getWorld();

                                        BlockEntity be = world.getBlockEntity(pos);

                                        if (be instanceof Labelable labelable) {
                                            labelable.setLabel(label);
                                        }

                                        return 1;
                                    }))
                            )
                    )
            );

            dispatcher.register(literal("setlabel")
                    .then(argument("location", Vec3ArgumentType.vec3())
                            .then(literal("item")
                                    .then(argument("display-item", ItemStackArgumentType.itemStack(registryAccess)).executes(context -> {
                                        BlockPos pos = Vec3ArgumentType.getPosArgument(context, "location").toAbsoluteBlockPos(context.getSource());
                                        Item item = ItemStackArgumentType.getItemStackArgument(context, "display-item").getItem();

                                        World world = context.getSource().getWorld();

                                        BlockEntity be = world.getBlockEntity(pos);

                                        if (be instanceof Labelable labelable) {
                                            labelable.setLabelDisplayItem(item);
                                        }

                                        return 1;
                                    }))
                            )
                    )
            );
        });
    }
}
/*

*/

/*
)
 */