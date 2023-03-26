package infinituum.chesttagger.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

import static infinituum.chesttagger.registration.ItemRegistration.LABELLING_MACHINE;

public class ChestTaggerRecipeProvider extends FabricRecipeProvider {

    public ChestTaggerRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, LABELLING_MACHINE)
                .pattern("CIC")
                .pattern("RPR")
                .pattern("CIC")
                .input('R', Items.REDSTONE)
                .input('C', Items.COPPER_INGOT)
                .input('P', Items.PAPER)
                .input('I', Items.IRON_INGOT)
                .criterion("has_items", InventoryChangedCriterion.Conditions.items(Items.PAPER, Items.COPPER_INGOT, Items.IRON_INGOT, Items.REDSTONE))
                .offerTo(exporter, new Identifier(output.getModId(), "labelling_machine_recipe"));
    }
}
