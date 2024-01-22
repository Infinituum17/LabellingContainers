package infinituum.labellingcontainers.fabric.providers.language;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import static infinituum.labellingcontainers.registration.ItemRegistration.LABEL_PRINTER;

public class SpanishLangProvider extends FabricLanguageProvider {
    public SpanishLangProvider(FabricDataOutput dataOutput,String languageCode) { super(dataOutput, languageCode); }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(LABEL_PRINTER.get(), "Etiquetador");

        String labelPrinterKey = LABEL_PRINTER.get().getTranslationKey();

        translationBuilder.add(labelPrinterKey + ".gui_display_name", "Etiquetador");
        translationBuilder.add(labelPrinterKey + ".tooltip.none", "Ninguno");
        translationBuilder.add(labelPrinterKey + ".tooltip.label", "Etiqueta: ");
        translationBuilder.add(labelPrinterKey + ".tooltip.display_item", "Icono: ");
        translationBuilder.add(labelPrinterKey + ".tooltip.hidden", "(Presiona shift para más info)");
        translationBuilder.add(labelPrinterKey + ".tooltip.description", "Utiliza papel para imprimir etiquetas nuevas");
        translationBuilder.add(labelPrinterKey + ".paper.error", "¡No tienes suficiente papel!");

        translationBuilder.add("block.labelable", "Utilice un Etiquetador para etiquetar este bloque");
    }
}
