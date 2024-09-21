package infinituum.labellingcontainers.fabric.providers.language;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

import static infinituum.labellingcontainers.registration.ItemRegistration.LABEL_PRINTER;

public final class SpanishLangProvider extends FabricLanguageProvider {
    public SpanishLangProvider(FabricDataOutput dataOutput, String languageCode, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, languageCode, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(LABEL_PRINTER.get(), "Etiquetador");

        String labelPrinterKey = LABEL_PRINTER.get().getDescriptionId();

        translationBuilder.add(labelPrinterKey + ".gui_display_name", "Etiquetador");
        translationBuilder.add(labelPrinterKey + ".tooltip.none", "Ninguno");
        translationBuilder.add(labelPrinterKey + ".tooltip.label", "Etiqueta: ");
        translationBuilder.add(labelPrinterKey + ".tooltip.display_item", "Icono: ");
        translationBuilder.add(labelPrinterKey + ".tooltip.hidden", "(Presiona shift para más info)");
        translationBuilder.add(labelPrinterKey + ".tooltip.description", "Utiliza papel para imprimir etiquetas nuevas");
        translationBuilder.add(labelPrinterKey + ".tooltip.mode", "Modo: ");
        translationBuilder.add(labelPrinterKey + ".paper.error", "¡No tienes suficiente papel!");
        translationBuilder.add(labelPrinterKey + ".untaggable.error", "¡No puedes etiquetar este bloque!");
        translationBuilder.add(labelPrinterKey + ".mode.create", "Creación");
        translationBuilder.add(labelPrinterKey + ".mode.copy", "Copia");
        translationBuilder.add(labelPrinterKey + ".mode.copy.success", "Copiado Exitosamente!");
        translationBuilder.add(labelPrinterKey + ".mode.copy.error", "¡No puedes copiar la etiqueta de este bloque!");
        translationBuilder.add("command.labelconfig.addition.success", "¡%s se agregó exitosamente a la configuración!");
        translationBuilder.add("command.labelconfig.removal.success", "¡%s fue eliminado exitosamente de la configuración!");

        translationBuilder.add("block.labelable", "Utilice un Etiquetador para etiquetar este bloque");
    }
}
