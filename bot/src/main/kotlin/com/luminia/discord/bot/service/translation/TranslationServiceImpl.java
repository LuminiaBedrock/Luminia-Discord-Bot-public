package com.luminia.discord.bot.service.translation;

import com.luminia.config.Config;
import com.luminia.discord.bot.translation.TranslationContainer;
import com.luminia.discord.bot.translation.TranslationProvider;
import com.luminia.discord.bot.utils.ConfigHelper;
import com.luminia.discord.bot.translation.LanguageCode;

import java.io.File;

public class TranslationServiceImpl implements TranslationService {

    private final LanguageCode fallbackLanguage;
    private final TranslationProvider provider;

    public TranslationServiceImpl(Config config) {
        this.fallbackLanguage = LanguageCode.getByName(config.node("language").asString());

        File translationFolder = new File("lang");
        translationFolder.mkdirs();

        for (LanguageCode code : LanguageCode.values()) {
            ConfigHelper.saveResource("lang/" + code.getName() + ".yml", false);
        }

        this.provider = new TranslationProvider();
        this.provider.init(translationFolder);
    }

    @Override
    public LanguageCode getLanguage() {
        return fallbackLanguage;
    }

    @Override
    public String translate(String key, Object... replacements) {
        return translate(key, fallbackLanguage, replacements);
    }

    @Override
    public String translate(String key, LanguageCode code, Object... replacements) {
        TranslationContainer container = provider.getContainer(key);
        if (container == null) return "null";

        String message = container.hasTranslation(code) ?
                container.getTranslation(code, "null") :
                container.getTranslation(fallbackLanguage, "null");

        int i = 0;
        for (Object replacement : replacements) {
            message = message.replace("[" + i + "]", String.valueOf(replacement));
            i++;
        }

        return message;
    }
}
