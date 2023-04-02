package infinituum.labellingcontainers.providers.language.spanish;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class SpanishVeLangProvider extends FabricLanguageProvider {
    public SpanishVeLangProvider(FabricDataOutput dataOutput) { super(dataOutput, "es_ve"); }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        new SpanishLangProvider(dataOutput).generateTranslations(translationBuilder);
    }
}
