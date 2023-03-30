package infinituum.chesttagger.providers.language;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import static infinituum.chesttagger.registration.ItemRegistration.LABEL_PRINTER;

public class ItalianLangProvider extends FabricLanguageProvider {
    public ItalianLangProvider(FabricDataOutput dataOutput) { super(dataOutput, "it_it"); }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(LABEL_PRINTER, "Stampante di etichette");
        translationBuilder.add(LABEL_PRINTER.getTranslationKey() + ".gui_display_name", "Stampante di etichette");
        translationBuilder.add(LABEL_PRINTER.getTranslationKey() + ".tooltip.none", "Nessuno");
        translationBuilder.add(LABEL_PRINTER.getTranslationKey() + ".tooltip.label", "Titolo: ");
        translationBuilder.add(LABEL_PRINTER.getTranslationKey() + ".tooltip.display_item", "Oggetto: ");
    }
}
