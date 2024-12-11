package infinituum.labellingcontainers.fabric;

import infinituum.labellingcontainers.fabric.providers.ModRecipeProvider;
import infinituum.labellingcontainers.fabric.providers.ModelProvider;
import infinituum.labellingcontainers.fabric.providers.language.EnglishLangProvider;
import infinituum.labellingcontainers.fabric.providers.language.ItalianLangProvider;
import infinituum.labellingcontainers.fabric.providers.language.SpanishLangProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class LabellingContainersDataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModRecipeProvider::new);
        pack.addProvider(ModelProvider::new);

        registerLanguages(pack);
    }

    private void registerLanguages(FabricDataGenerator.Pack pack) {
        pack.addProvider(EnglishLangProvider::new);
        pack.addProvider(ItalianLangProvider::new);

        // Spanish translation by Krb (https://github.com/Krb0)
        List<String> languageCodes = List.of("es_mx", "es_ve", "es_es", "es_ar", "es_ec", "es_cl", "es_uy");
        languageCodes.forEach(
                (languageCode) -> pack.addProvider(
                        (FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) ->
                                new SpanishLangProvider(dataOutput, languageCode, registriesFuture)));
    }
}
