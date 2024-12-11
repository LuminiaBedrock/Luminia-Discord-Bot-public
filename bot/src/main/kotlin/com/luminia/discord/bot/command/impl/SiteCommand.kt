package com.luminia.discord.bot.command.impl

import com.luminia.discord.api.command.SlashCommand
import com.luminia.discord.api.command.data.CommandData
import com.luminia.discord.bot.service.translation.TranslationFormat
import com.luminia.discord.bot.service.translation.TranslationService
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class SiteCommand : SlashCommand() {

    private val translationService = TranslationService.getInstance()

    override fun getCommandData(): CommandData {
        return CommandData.slash("site", TranslationFormat.commandDescription("site"))
    }

    override fun execute(event: SlashCommandInteractionEvent) {
        event.reply(translationService.translate("command-site-message-success")).queue()
    }
}