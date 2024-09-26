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
        
        translationBuilder.add("block.labelable", "Utilice un Etiquetador para etiquetar este bloque");

        translateCommands(translationBuilder);
    }

    private void translateCommands(TranslationBuilder translationBuilder) {
        String setLabelKey = "commands.setlabel";
        translationBuilder.add(setLabelKey + ".label.success", "La etiqueta \"%s\" se ha establecido en el bloque en la posición %d %d %d");
        translationBuilder.add(setLabelKey + ".label.invalid", "La etiqueta \"%s\" no se puede configurar para bloquear en la posición %d %d %d");
        translationBuilder.add(setLabelKey + ".label.failed", "La identificación \"%s\" correspondiente al bloque en la posición %d %d %d no se encontró en el archivo de configuración. Para agregarlo, use el comando '/labelconfig add-item %s'");
        translationBuilder.add(setLabelKey + ".displayitem.success", "El item de visualización \"%s\" se ha establecido en el bloque en la posición %d %d %d");
        translationBuilder.add(setLabelKey + ".displayitem.invalid", "El item de visualización \"%s\" no se puede configurar para bloquear en la posición %d %d %d");
        translationBuilder.add(setLabelKey + ".displayitem.failed", "La identificación \"%s\" correspondiente al bloque en la posición %d %d %d no se encontró en el archivo de configuración. Para agregarlo, use el comando '/labelconfig add-item %s'");

        String labelConfigKey = "commands.labelconfig";
        translationBuilder.add(labelConfigKey + ".addhand.player.invalid", "Fuente de comando no válida");
        translationBuilder.add(labelConfigKey + ".addhand.resource.invalid", "No se pudo encontrar la identificación del recurso para el item \"%s\"");
        translationBuilder.add(labelConfigKey + ".addhand.context.failure", "Solo puedes ejecutar este comando como jugador");
        translationBuilder.add(labelConfigKey + ".removehand.player.invalid", "Fuente de comando no válida");
        translationBuilder.add(labelConfigKey + ".removehand.resource.invalid", "No se pudo encontrar la identificación del recurso para el item \"%s\"");
        translationBuilder.add(labelConfigKey + ".removehand.context.failure", "Solo puedes ejecutar este comando como jugador");
        translationBuilder.add(labelConfigKey + ".additem.resource.invalid", "No se pudo encontrar la identificación del recurso para el item \"%s\"");
        translationBuilder.add(labelConfigKey + ".removeitem.resource.invalid", "No se pudo encontrar la identificación del recurso para el item \"%s\"");
        translationBuilder.add(labelConfigKey + ".add.id.no_change", "El item \"%s\" ya estaba presente en el archivo de configuración");
        translationBuilder.add(labelConfigKey + ".add.id.success", "El item \"%s\" se ha agregado al archivo de configuración");
        translationBuilder.add(labelConfigKey + ".remove.id.no_change", "El item \"%s\" no está presente en el archivo de configuración");
        translationBuilder.add(labelConfigKey + ".remove.id.success", "El item \"%s\" ha sido eliminado del archivo de configuración");
        translationBuilder.add(labelConfigKey + ".add.tag.no_change", "La item-tag \"%s\" ya estaba presente en el archivo de configuración");
        translationBuilder.add(labelConfigKey + ".add.tag.success", "La item-tag \"%s\" se ha agregado al archivo de configuración");
        translationBuilder.add(labelConfigKey + ".remove.tag.no_change", "La item-tag \"%s\" no está presente en el archivo de configuración");
        translationBuilder.add(labelConfigKey + ".remove.tag.success", "La item-tag \"%s\" ha sido eliminado del archivo de configuración");

        String labelPositionKey = "commands.labelposition";
        translationBuilder.add(labelPositionKey + ".set.player.invalid", "Solo puedes ejecutar este comando como jugador");
        translationBuilder.add(labelPositionKey + ".set.success", "La posición de la etiqueta \"%s\" se configuró en el archivo de configuración");
    }
}
