package infinituum.chesttagger;

import infinituum.chesttagger.providers.ChestTaggerEnglishLangProvider;
import infinituum.chesttagger.providers.ChestTaggerModelProvider;
import infinituum.chesttagger.providers.ChestTaggerRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ChestTaggerDataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.createPack().addProvider(ChestTaggerRecipeProvider::new);
        fabricDataGenerator.createPack().addProvider(ChestTaggerEnglishLangProvider::new);
        fabricDataGenerator.createPack().addProvider(ChestTaggerModelProvider::new);
    }
}
