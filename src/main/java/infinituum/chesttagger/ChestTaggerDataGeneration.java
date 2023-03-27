package infinituum.chesttagger;

import infinituum.chesttagger.providers.language.EnglishLangProvider;
import infinituum.chesttagger.providers.ModelProvider;
import infinituum.chesttagger.providers.RecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ChestTaggerDataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.createPack().addProvider(RecipeProvider::new);
        fabricDataGenerator.createPack().addProvider(EnglishLangProvider::new);
        fabricDataGenerator.createPack().addProvider(ModelProvider::new);
    }
}
