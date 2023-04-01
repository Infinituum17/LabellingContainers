package infinituum.labellingcontainers.providers.language;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import static infinituum.labellingcontainers.registration.ItemRegistration.LABEL_PRINTER;

public class EnglishLangProvider extends FabricLanguageProvider {
    public EnglishLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(LABEL_PRINTER, "Label Printer");
        translationBuilder.add(LABEL_PRINTER.getTranslationKey() + ".gui_display_name", "Label Printer");
        translationBuilder.add(LABEL_PRINTER.getTranslationKey() + ".tooltip.none", "None");
        translationBuilder.add(LABEL_PRINTER.getTranslationKey() + ".tooltip.label", "Label: ");
        translationBuilder.add(LABEL_PRINTER.getTranslationKey() + ".tooltip.display_item", "Icon: ");

        translationBuilder.add("block.labelable", "Can be labeled");
    }
}
