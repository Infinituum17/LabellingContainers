package infinituum.labellingcontainers.providers.language;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import static infinituum.labellingcontainers.registration.ItemRegistration.LABEL_PRINTER;

public class ItalianLangProvider extends FabricLanguageProvider {
    public ItalianLangProvider(FabricDataGenerator dataOutput) { super(dataOutput, "it_it"); }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(LABEL_PRINTER, "Stampante di etichette");

        String labelPrinterKey = LABEL_PRINTER.getTranslationKey();

        translationBuilder.add(labelPrinterKey + ".gui_display_name", "Stampante di etichette");
        translationBuilder.add(labelPrinterKey + ".tooltip.none", "Nessuno");
        translationBuilder.add(labelPrinterKey + ".tooltip.label", "Etichetta: ");
        translationBuilder.add(labelPrinterKey + ".tooltip.display_item", "Icona: ");
        translationBuilder.add(labelPrinterKey + ".tooltip.hidden", "(Premi shift per più informazioni)");
        translationBuilder.add(labelPrinterKey + ".tooltip.description", "Usa la carta per stampare nuove etichette");
        translationBuilder.add(labelPrinterKey + ".paper.error", "Non hai abbastanza carta!");

        translationBuilder.add("block.labelable", "Può essere etichettato");
    }
}
