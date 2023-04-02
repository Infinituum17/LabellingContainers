package infinituum.labellingcontainers.providers.language.spanish;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class SpanishMxLangProvider extends FabricLanguageProvider {
    public SpanishMxLangProvider(FabricDataOutput dataOutput) { super(dataOutput, "es_mx"); }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        new SpanishLangProvider(dataOutput).generateTranslations(translationBuilder);
    }
}
