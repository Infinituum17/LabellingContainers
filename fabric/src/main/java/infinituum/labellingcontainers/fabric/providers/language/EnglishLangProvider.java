package infinituum.labellingcontainers.fabric.providers.language;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

import static infinituum.labellingcontainers.registration.ItemRegistration.LABEL_PRINTER;

public final class EnglishLangProvider extends FabricLanguageProvider {

    public EnglishLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(LABEL_PRINTER.get(), "Label Printer");

        String labelPrinterKey = LABEL_PRINTER.get().getDescriptionId();

        translationBuilder.add(labelPrinterKey + ".gui_display_name", "Label Printer");
        translationBuilder.add(labelPrinterKey + ".tooltip.none", "None");
        translationBuilder.add(labelPrinterKey + ".tooltip.label", "Label: ");
        translationBuilder.add(labelPrinterKey + ".tooltip.display_item", "Icon: ");
        translationBuilder.add(labelPrinterKey + ".tooltip.hidden", "(Press shift for more info)");
        translationBuilder.add(labelPrinterKey + ".tooltip.description", "Uses paper to print new labels");
        translationBuilder.add(labelPrinterKey + ".tooltip.mode", "Mode: ");
        translationBuilder.add(labelPrinterKey + ".paper.error", "You don't have enough paper!");
        translationBuilder.add(labelPrinterKey + ".untaggable.error", "You can't add a label to this block!");
        translationBuilder.add(labelPrinterKey + ".mode.create", "Creation");
        translationBuilder.add(labelPrinterKey + ".mode.copy", "Copy");
        translationBuilder.add(labelPrinterKey + ".mode.copy.success", "Successfully Copied!");
        translationBuilder.add(labelPrinterKey + ".mode.copy.error", "You can't copy this block's label!");

        translationBuilder.add("block.labelable", "Use a Label Printer to label this block");

        translateCommands(translationBuilder);
    }

    private void translateCommands(TranslationBuilder translationBuilder) {
        String setLabelKey = "commands.setlabel";
        translationBuilder.add(setLabelKey + ".label.success", "The label \"%s\" has been set to the block at position %d %d %d");
        translationBuilder.add(setLabelKey + ".label.invalid", "The label \"%s\" cannot be set to block at position %d %d %d");
        translationBuilder.add(setLabelKey + ".label.failed", "The id \"%s\" corresponding to the block at position %d %d %d was not found in the configuration file. To add it, use the command '/labelconfig add-item %s'");
        translationBuilder.add(setLabelKey + ".displayitem.success", "The display item \"%s\" has been set to the block at position %d %d %d");
        translationBuilder.add(setLabelKey + ".displayitem.invalid", "The display item \"%s\" cannot be set to block at position %d %d %d");
        translationBuilder.add(setLabelKey + ".displayitem.failed", "The id \"%s\" corresponding to the block at position %d %d %d was not found in the configuration file. To add it, use the command '/labelconfig add-item %s'");

        String labelConfigKey = "commands.labelconfig";
        translationBuilder.add(labelConfigKey + ".addhand.player.invalid", "Invalid command source");
        translationBuilder.add(labelConfigKey + ".addhand.resource.invalid", "Could not find the resource id for the item \"%s\"");
        translationBuilder.add(labelConfigKey + ".addhand.context.failure", "You can only run this command as a player");
        translationBuilder.add(labelConfigKey + ".removehand.player.invalid", "Invalid command source");
        translationBuilder.add(labelConfigKey + ".removehand.resource.invalid", "Could not find the resource id for the item \"%s\"");
        translationBuilder.add(labelConfigKey + ".removehand.context.failure", "You can only run this command as a player");
        translationBuilder.add(labelConfigKey + ".additem.resource.invalid", "Could not find the resource id for the item \"%s\"");
        translationBuilder.add(labelConfigKey + ".removeitem.resource.invalid", "Could not find the resource id for the item \"%s\"");
        translationBuilder.add(labelConfigKey + ".add.id.no_change", "The item \"%s\" was already present in the configuration file");
        translationBuilder.add(labelConfigKey + ".add.id.success", "The item \"%s\" has been added to the configuration file");
        translationBuilder.add(labelConfigKey + ".remove.id.no_change", "The item \"%s\" is not present in the configuration file");
        translationBuilder.add(labelConfigKey + ".remove.id.success", "The item \"%s\" has been removed from the configuration file");
        translationBuilder.add(labelConfigKey + ".add.tag.no_change", "The tag \"%s\" was already present in the configuration file");
        translationBuilder.add(labelConfigKey + ".add.tag.success", "The tag \"%s\" has been added to the configuration file");
        translationBuilder.add(labelConfigKey + ".remove.tag.no_change", "The tag \"%s\" is not present in the configuration file");
        translationBuilder.add(labelConfigKey + ".remove.tag.success", "The tag \"%s\" has been removed from the configuration file");

        String labelPositionKey = "commands.labelposition";
        translationBuilder.add(labelPositionKey + ".set.player.invalid", "You can only run this command as a player");
        translationBuilder.add(labelPositionKey + ".set.success", "The label position \"%s\" was set in the configuration file");
    }
}
