package infinituum.labellingcontainers;

import infinituum.labellingcontainers.providers.language.EnglishLangProvider;
import infinituum.labellingcontainers.providers.ModelProvider;
import infinituum.labellingcontainers.providers.RecipeProvider;
import infinituum.labellingcontainers.providers.language.ItalianLangProvider;
import infinituum.labellingcontainers.providers.language.spanish.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class LabellingContainersDataGeneration implements DataGeneratorEntrypoint {
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

        // Spanish
        pack.addProvider(SpanishLangProvider::new);
        pack.addProvider(SpanishMxLangProvider::new);
        pack.addProvider(SpanishArLangProvider::new);
        pack.addProvider(SpanishUyLangProvider::new);
        pack.addProvider(SpanishEcLangProvider::new);
        pack.addProvider(SpanishClLangProvider::new);
        pack.addProvider(SpanishVeLangProvider::new);
    }
}
