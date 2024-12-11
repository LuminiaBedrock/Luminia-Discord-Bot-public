package com.luminia.discord.bot.translation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TranslationContainer {

    private final Map<LanguageCode, String> translations = new HashMap<>();

    public static final TranslationContainer EMPTY = new TranslationContainer() {

        @Override
        public Map<LanguageCode, String> getTranslations() {
            return Collections.emptyMap();
        }

        @Override
        public String getTranslation(LanguageCode code, String defaultValue) {
            return null;
        }

        @Override
        public void addTranslation(LanguageCode code, String message) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasTranslation(LanguageCode code) {
            return false;
        }
    };

    public Map<LanguageCode, String> getTranslations() {
        return translations;
    }

    public String getTranslation(LanguageCode code, String defaultValue) {
        return translations.getOrDefault(code, defaultValue);
    }

    public void addTranslation(LanguageCode code, String message) {
        translations.put(code, message);
    }

    public boolean hasTranslation(LanguageCode code) {
        return translations.containsKey(code);
    }
}