package infinituum.labellingcontainers.providers.language.spanish;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class SpanishUyLangProvider extends FabricLanguageProvider {
    public SpanishUyLangProvider(FabricDataOutput dataOutput) { super(dataOutput, "es_uy"); }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        new SpanishLangProvider(dataOutput).generateTranslations(translationBuilder);
    }
}
