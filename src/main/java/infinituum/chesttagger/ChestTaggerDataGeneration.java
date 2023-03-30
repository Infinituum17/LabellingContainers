package infinituum.chesttagger;

import infinituum.chesttagger.providers.language.EnglishLangProvider;
import infinituum.chesttagger.providers.ModelProvider;
import infinituum.chesttagger.providers.RecipeProvider;
import infinituum.chesttagger.providers.language.ItalianLangProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ChestTaggerDataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(RecipeProvider::new);
        pack.addProvider(ModelProvider::new);

        registerLanguages(pack);
    }

    private void registerLanguages(FabricDataGenerator.Pack pack) {
        pack.addProvider(EnglishLangProvider::new);
        pack.addProvider(ItalianLangProvider::new);
    }
}
