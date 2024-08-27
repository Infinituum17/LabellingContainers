package infinituum.labellingcontainers.fabric.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import static infinituum.labellingcontainers.registration.ItemRegistration.LABEL_PRINTER;

public final class RecipeProvider extends FabricRecipeProvider {

    public RecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, LABEL_PRINTER.get())
                .pattern("CIC")
                .pattern("RSR")
                .pattern("CIC")
                .define('R', Items.REDSTONE)
                .define('C', Items.COPPER_INGOT)
                .define('S', Items.INK_SAC)
                .define('I', Items.IRON_INGOT)
                .unlockedBy("has_items", InventoryChangeTrigger.TriggerInstance.hasItems(Items.INK_SAC, Items.COPPER_INGOT, Items.IRON_INGOT, Items.REDSTONE))
                .save(exporter, new ResourceLocation(output.getModId(), "label_printer_recipe"));
    }
}
