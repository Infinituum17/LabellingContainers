package infinituum.labellingcontainers.fabric.providers.language;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import static infinituum.labellingcontainers.registration.ItemRegistration.LABEL_PRINTER;

public class ItalianLangProvider extends FabricLanguageProvider {
    public ItalianLangProvider(FabricDataOutput dataOutput) { super(dataOutput, "it_it"); }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(LABEL_PRINTER.get(), "Stampante di etichette");

        String labelPrinterKey = LABEL_PRINTER.get().getDescriptionId();

        translationBuilder.add(labelPrinterKey + ".gui_display_name", "Stampante di etichette");
        translationBuilder.add(labelPrinterKey + ".tooltip.none", "Nessuno");
        translationBuilder.add(labelPrinterKey + ".tooltip.label", "Etichetta: ");
        translationBuilder.add(labelPrinterKey + ".tooltip.display_item", "Icona: ");
        translationBuilder.add(labelPrinterKey + ".tooltip.hidden", "(Premi shift per più informazioni)");
        translationBuilder.add(labelPrinterKey + ".tooltip.description", "Usa la carta per stampare nuove etichette");
        translationBuilder.add(labelPrinterKey + ".paper.error", "Non hai abbastanza carta!");

        translationBuilder.add("block.labelable", "Usa una Stampante di etichette per etichettare questo blocco");
    }
}
