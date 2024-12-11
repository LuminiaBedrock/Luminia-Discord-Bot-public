package com.luminia.discord.bot.service.translation

import com.luminia.discord.bot.translation.LanguageCode

object TranslationFormat {

    fun commandDescription(name: String, code: LanguageCode = LanguageCode.RUS): String {
        return TranslationService.getInstance().translate("command-$name-description", code)
    }

    fun commandSubcommand(name: String, subcommand: String, code: LanguageCode = LanguageCode.RUS): String {
        return TranslationService.getInstance().translate("command-$name-subcommand-$subcommand-name", code)
    }

    fun commandOption(name: String, option: String, code: LanguageCode = LanguageCode.RUS): String {
        return TranslationService.getInstance().translate("command-$name-option-$option-name", code)
    }
}