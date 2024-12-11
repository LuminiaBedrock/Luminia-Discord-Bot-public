package com.luminia.discord.bot.service.translation;

import com.luminia.discord.bot.DiscordBot;
import com.luminia.discord.bot.translation.LanguageCode;

public interface TranslationService {

    LanguageCode getLanguage();

    String translate(String key, Object... replacements);

    String translate(String key, LanguageCode code, Object... replacements);

    static TranslationService getInstance() {
        return DiscordBot.getInstance().getTranslationService();
    }
}
