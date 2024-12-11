package com.luminia.discord.bot.command.impl;

import com.luminia.discord.api.command.SlashCommand
import com.luminia.discord.api.command.data.CommandData
import com.luminia.discord.bot.service.translation.TranslationFormat
import com.luminia.discord.bot.service.translation.TranslationService
import com.luminia.discord.bot.utils.BotColors
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class MemberCountCommand : SlashCommand() {

    private val translationService = TranslationService.getInstance()

    override fun getCommandData(): CommandData {
        return CommandData.slash("membercount", TranslationFormat.commandDescription("member-count"))
    }

    override fun execute(event: SlashCommandInteractionEvent) {
        event.guild?.let { guild ->
            val embed = EmbedBuilder()
                .setTitle(translationService.translate("command-member-count-success-title"))
                .setDescription(translationService.translate("command-member-count-success-description", guild.memberCount))
                .setColor(BotColors.PRIMARY)
                .build()
            event.replyEmbeds(embed).queue()
        }
    }
}
