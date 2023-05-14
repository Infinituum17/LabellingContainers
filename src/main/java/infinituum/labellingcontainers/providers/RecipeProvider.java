package infinituum.labellingcontainers.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

import static infinituum.labellingcontainers.registration.ItemRegistration.LABEL_PRINTER;

public class RecipeProvider extends FabricRecipeProvider {

    public RecipeProvider(FabricDataGenerator output) {
        super(output);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(LABEL_PRINTER, 1)
                .pattern("CIC")
                .pattern("RPR")
                .pattern("CIC")
                .input('R', Items.REDSTONE)
                .input('C', Items.COPPER_INGOT)
                .input('P', Items.PAPER)
                .input('I', Items.IRON_INGOT)
                .criterion("has_items", InventoryChangedCriterion.Conditions.items(Items.PAPER, Items.COPPER_INGOT, Items.IRON_INGOT, Items.REDSTONE))
                .offerTo(exporter, new Identifier(dataGenerator.getModId(), "label_printer_recipe"));
    }
}
