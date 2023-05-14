package infinituum.labellingcontainers.providers.language;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import static infinituum.labellingcontainers.registration.ItemRegistration.LABEL_PRINTER;

public class ItalianLangProvider extends FabricLanguageProvider {
    public ItalianLangProvider(FabricDataGenerator dataOutput) { super(dataOutput, "it_it"); }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(LABEL_PRINTER, "Stampante di etichette");
        translationBuilder.add(LABEL_PRINTER.getTranslationKey() + ".gui_display_name", "Stampante di etichette");
        translationBuilder.add(LABEL_PRINTER.getTranslationKey() + ".tooltip.none", "Nessuno");
        translationBuilder.add(LABEL_PRINTER.getTranslationKey() + ".tooltip.label", "Etichetta: ");
        translationBuilder.add(LABEL_PRINTER.getTranslationKey() + ".tooltip.display_item", "Icona: ");

        translationBuilder.add("block.labelable", "Può essere etichettato");
    }
}
