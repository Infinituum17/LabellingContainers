package infinituum.labellingcontainers.events;

import com.mojang.brigadier.ImmutableStringReader;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.CommandPerformEvent;
import org.jetbrains.annotations.NotNull;

import static infinituum.labellingcontainers.LabellingContainers.LOGGER;
import static infinituum.labellingcontainers.LabellingContainersConfig.TAGGABLE_BLOCKS_CONFIG;

public class ReloadCommandHandler {
    public static EventResult handle(@NotNull CommandPerformEvent commandPerformEvent) {
        ImmutableStringReader reader = commandPerformEvent.getResults().getReader();

        if (reader.getRead().split(" ")[0].equals("reload")) {
            TAGGABLE_BLOCKS_CONFIG.reloadConfig();

            LOGGER.info("Reloading configs...");
        }

        return EventResult.pass();
    }
}
