package infinituum.labellingcontainers.fabric.providers.language;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

import static infinituum.labellingcontainers.registration.ItemRegistration.LABEL_PRINTER;

public final class ItalianLangProvider extends FabricLanguageProvider {

    public ItalianLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "it_it", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(LABEL_PRINTER.get(), "Stampante di etichette");

        String labelPrinterKey = LABEL_PRINTER.get().getDescriptionId();

        translationBuilder.add(labelPrinterKey + ".gui_display_name", "Stampante di etichette");
        translationBuilder.add(labelPrinterKey + ".tooltip.none", "Nessuno");
        translationBuilder.add(labelPrinterKey + ".tooltip.label", "Etichetta: ");
        translationBuilder.add(labelPrinterKey + ".tooltip.display_item", "Icona: ");
        translationBuilder.add(labelPrinterKey + ".tooltip.hidden", "(Premi shift per più informazioni)");
        translationBuilder.add(labelPrinterKey + ".tooltip.description", "Usa la carta per stampare nuove etichette");
        translationBuilder.add(labelPrinterKey + ".tooltip.mode", "Modalità: ");
        translationBuilder.add(labelPrinterKey + ".paper.error", "Non hai abbastanza carta!");
        translationBuilder.add(labelPrinterKey + ".untaggable.error", "Non puoi etichettare questo blocco!");
        translationBuilder.add(labelPrinterKey + ".mode.create", "Creazione");
        translationBuilder.add(labelPrinterKey + ".mode.copy", "Copia");
        translationBuilder.add(labelPrinterKey + ".mode.copy.success", "Copiato con Successo!");
        translationBuilder.add(labelPrinterKey + ".mode.copy.error", "Non puoi copiare l'etichetta di questo blocco!");

        translationBuilder.add("block.labelable", "Usa una Stampante di etichette per etichettare questo blocco");

        translateCommands(translationBuilder);
    }

    private void translateCommands(TranslationBuilder translationBuilder) {
        String setLabelKey = "commands.setlabel";
        translationBuilder.add(setLabelKey + ".label.success", "L'etichetta \"%s\" è stata impostata sul blocco alla posizione %d %d %d");
        translationBuilder.add(setLabelKey + ".label.invalid", "L'etichetta \"%s\" non può essere impostata sul blocco alla posizione %d %d %d");
        translationBuilder.add(setLabelKey + ".label.failed", "L'id \"%s\" che corrisponde al blocco alla posizione %d %d %d non è stato trovato nel file di configurazione. Per aggiungerlo, usa il comando '/labelconfig add-item %s'");
        translationBuilder.add(setLabelKey + ".displayitem.success", "L'oggetto icona \"%s\" è stato impostato sul blocco alla posizione %d %d %d");
        translationBuilder.add(setLabelKey + ".displayitem.invalid", "L'oggetto icona \"%s\" non può essere impostato sul blocco alla posizione %d %d %d");
        translationBuilder.add(setLabelKey + ".displayitem.failed", "L'id \"%s\" che corrisponde al blocco alla posizione %d %d %d non è stato trovato nel file di configurazione. Per aggiungerlo, usa il comando '/labelconfig add-item %s'");

        String labelConfigKey = "commands.labelconfig";
        translationBuilder.add(labelConfigKey + ".addhand.player.invalid", "Sorgente del comando non valida");
        translationBuilder.add(labelConfigKey + ".addhand.resource.invalid", "Non è stato possibile trovare l'id risorsa per l'oggetto \"%s\"");
        translationBuilder.add(labelConfigKey + ".addhand.context.failure", "Puoi eseguire questo comando solo come giocatore");
        translationBuilder.add(labelConfigKey + ".removehand.player.invalid", "Sorgente del comando non valida");
        translationBuilder.add(labelConfigKey + ".removehand.resource.invalid", "Non è stato possibile trovare l'id risorsa per l'oggetto \"%s\"");
        translationBuilder.add(labelConfigKey + ".removehand.context.failure", "Puoi eseguire questo comando solo come giocatore");
        translationBuilder.add(labelConfigKey + ".additem.resource.invalid", "Non è stato possibile trovare l'id risorsa per l'oggetto \"%s\"");
        translationBuilder.add(labelConfigKey + ".removeitem.resource.invalid", "Non è stato possibile trovare l'id risorsa per l'oggetto \"%s\"");
        translationBuilder.add(labelConfigKey + ".add.id.no_change", "L'oggetto \"%s\" era già presente nel file di configurazione");
        translationBuilder.add(labelConfigKey + ".add.id.success", "L'oggetto \"%s\" è stato aggiunto al file di configurazione");
        translationBuilder.add(labelConfigKey + ".remove.id.no_change", "L'oggetto \"%s\" non è presente nel file di configurazione");
        translationBuilder.add(labelConfigKey + ".remove.id.success", "L'oggetto \"%s\" è stato rimosso dal file di configurazione");
        translationBuilder.add(labelConfigKey + ".add.tag.no_change", "Il tag \"%s\" era già presente nel file di configurazione");
        translationBuilder.add(labelConfigKey + ".add.tag.success", "Il tag \"%s\" è stato aggiunto al file di configurazione");
        translationBuilder.add(labelConfigKey + ".remove.tag.no_change", "Il tag \"%s\" non è presente nel file di configurazione");
        translationBuilder.add(labelConfigKey + ".remove.tag.success", "Il tag \"%s\" è stato rimosso dal file di configurazione");

        String labelPositionKey = "commands.labelposition";
        translationBuilder.add(labelPositionKey + ".set.player.invalid", "Puoi eseguire questo comando solo come giocatore");
        translationBuilder.add(labelPositionKey + ".set.success", "La posizione dell'etichetta \"%s\" è stata impostata nel file di configurazione");
    }
}
