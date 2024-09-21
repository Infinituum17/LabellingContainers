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
        translationBuilder.add("command.labelconfig.addition.success", "%s è stato aggiunto al config con successo!");
        translationBuilder.add("command.labelconfig.removal.success", "%s è stato rimosso dal config con successo!");

        translationBuilder.add("block.labelable", "Usa una Stampante di etichette per etichettare questo blocco");
    }
}
