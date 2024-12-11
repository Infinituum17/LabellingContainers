package infinituum.labellingcontainers.fabric.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static infinituum.labellingcontainers.LabellingContainers.MOD_ID;
import static infinituum.labellingcontainers.registration.ItemRegistration.LABEL_PRINTER;

public final class ModRecipeProvider extends FabricRecipeProvider {
    private static final ResourceLocation LABEL_PRINTER_RECIPE_RESOURCE_KEY = ResourceLocation.fromNamespaceAndPath(MOD_ID, "label_printer_recipe");

    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput exporter) {
        return new RecipeProvider(registries, exporter) {
            @Override
            public void buildRecipes() {
                ShapedRecipeBuilder.shaped(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.TOOLS, LABEL_PRINTER.get())
                        .pattern("CIC")
                        .pattern("RSR")
                        .pattern("CIC")
                        .define('R', Items.REDSTONE)
                        .define('C', Items.COPPER_INGOT)
                        .define('S', Items.INK_SAC)
                        .define('I', Items.IRON_INGOT)
                        .unlockedBy("has_items", InventoryChangeTrigger.TriggerInstance.hasItems(Items.INK_SAC, Items.COPPER_INGOT, Items.IRON_INGOT, Items.REDSTONE))
                        .save(exporter, ResourceKey.create(Registries.RECIPE, LABEL_PRINTER_RECIPE_RESOURCE_KEY));
            }
        };
    }

    @Override
    public @NotNull String getName() {
        return LABEL_PRINTER_RECIPE_RESOURCE_KEY.toString();
    }
}
