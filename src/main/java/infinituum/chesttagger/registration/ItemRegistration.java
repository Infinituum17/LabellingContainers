package infinituum.chesttagger.registration;

import infinituum.chesttagger.items.LabellingMachineItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static infinituum.chesttagger.ChestTagger.MODID;

public class ItemRegistration {
    public static LabellingMachineItem LABELLING_MACHINE = new LabellingMachineItem(new FabricItemSettings());

    @SuppressWarnings("UnstableApiUsage")
    public static void init() {
        Registry.register(Registries.ITEM, new Identifier(MODID, "labelling_machine"), LABELLING_MACHINE);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content
                .addAfter(Items.NAME_TAG, LABELLING_MACHINE)
        );
    }
}
