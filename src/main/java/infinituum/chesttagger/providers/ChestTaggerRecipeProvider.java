package infinituum.chesttagger.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
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
                .pattern("PPP")
                .pattern("PCP")
                .pattern("PPP")
                .input('C', TagKey.of(RegistryKeys.ITEM, new Identifier("c", "copper_ingots")))
                .input('P', Items.PAPER)
                .criterion("has_items", InventoryChangedCriterion.Conditions.items(Items.PAPER, Items.COPPER_INGOT))
                .offerTo(exporter, new Identifier(output.getModId(), "labelling_machine_recipe"));
    }
}
