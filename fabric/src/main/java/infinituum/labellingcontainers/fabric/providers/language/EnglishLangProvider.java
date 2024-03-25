package infinituum.labellingcontainers.fabric.providers.language;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import static infinituum.labellingcontainers.registration.ItemRegistration.LABEL_PRINTER;

public class EnglishLangProvider extends FabricLanguageProvider {
    public EnglishLangProvider(FabricDataGenerator dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(LABEL_PRINTER.get(), "Label Printer");

        String labelPrinterKey = LABEL_PRINTER.get().getDescriptionId();

        translationBuilder.add(labelPrinterKey + ".gui_display_name", "Label Printer");
        translationBuilder.add(labelPrinterKey + ".tooltip.none", "None");
        translationBuilder.add(labelPrinterKey + ".tooltip.label", "Label: ");
        translationBuilder.add(labelPrinterKey + ".tooltip.display_item", "Icon: ");
        translationBuilder.add(labelPrinterKey + ".tooltip.hidden", "(Press shift for more info)");
        translationBuilder.add(labelPrinterKey + ".tooltip.description", "Uses paper to print new labels");
        translationBuilder.add(labelPrinterKey + ".paper.error", "You don't have enough paper!");
        translationBuilder.add(labelPrinterKey + ".untaggable.error", "You can't tag this block!");

        translationBuilder.add("block.labelable", "Use a Label Printer to label this block");
    }
}
