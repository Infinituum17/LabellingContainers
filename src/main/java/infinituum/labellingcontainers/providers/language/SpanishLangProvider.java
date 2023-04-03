package infinituum.labellingcontainers.providers.language;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import static infinituum.labellingcontainers.registration.ItemRegistration.LABEL_PRINTER;

public class SpanishLangProvider extends FabricLanguageProvider {
    public SpanishLangProvider(FabricDataOutput dataOutput,String languageCode) { super(dataOutput, languageCode); }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(LABEL_PRINTER, "Etiquetador");
        translationBuilder.add(LABEL_PRINTER.getTranslationKey() + ".gui_display_name", "Etiquetador");
        translationBuilder.add(LABEL_PRINTER.getTranslationKey() + ".tooltip.none", "Ninguno");
        translationBuilder.add(LABEL_PRINTER.getTranslationKey() + ".tooltip.label", "Etiqueta: ");
        translationBuilder.add(LABEL_PRINTER.getTranslationKey() + ".tooltip.display_item", "Icono: ");

        translationBuilder.add("block.labelable", "Puede ser etiquetado");
    }
}
