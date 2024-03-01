package infinituum.labellingcontainers.fabric.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import java.util.function.Consumer;

import static infinituum.labellingcontainers.registration.ItemRegistration.LABEL_PRINTER;

public class RecipeProvider extends FabricRecipeProvider {

    public RecipeProvider(FabricDataGenerator output) {
        super(output);
    }

    @Override
    public void generateRecipes(Consumer<FinishedRecipe> exporter) {
        ShapedRecipeBuilder.shaped(LABEL_PRINTER.get())
                .pattern("CIC")
                .pattern("RSR")
                .pattern("CIC")
                .define('R', Items.REDSTONE)
                .define('C', Items.COPPER_INGOT)
                .define('S', Items.INK_SAC)
                .define('I', Items.IRON_INGOT)
                .unlockedBy("has_items", InventoryChangeTrigger.TriggerInstance.hasItems(Items.INK_SAC, Items.COPPER_INGOT, Items.IRON_INGOT, Items.REDSTONE))
                .save(exporter, new ResourceLocation(dataGenerator.getModId(), "label_printer_recipe"));
    }
}
