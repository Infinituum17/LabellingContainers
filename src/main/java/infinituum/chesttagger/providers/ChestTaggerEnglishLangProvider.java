package infinituum.chesttagger.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import static infinituum.chesttagger.registration.ItemRegistration.LABELLING_MACHINE;

public class ChestTaggerEnglishLangProvider extends FabricLanguageProvider {
    public ChestTaggerEnglishLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(LABELLING_MACHINE, "Labelling Machine");
        translationBuilder.add(LABELLING_MACHINE.getTranslationKey() + ".gui_display_name", "Labelling Machine");
        translationBuilder.add(LABELLING_MACHINE.getTranslationKey() + ".tooltip.label", "Label: ");
        translationBuilder.add(LABELLING_MACHINE.getTranslationKey() + ".tooltip.display_item", "Item: ");
    }
}
