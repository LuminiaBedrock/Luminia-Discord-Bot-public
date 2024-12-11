package com.luminia.discord.bot.command.impl;

import com.luminia.discord.api.command.SlashCommand
import com.luminia.discord.api.command.data.CommandData
import com.luminia.discord.bot.service.translation.TranslationFormat
import com.luminia.discord.bot.service.translation.TranslationService
import com.luminia.discord.bot.utils.BotColors
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData

class AvatarCommand : SlashCommand() {

    private val translationService = TranslationService.getInstance()

    override fun getCommandData(): CommandData {
        return CommandData.slash("avatar", TranslationFormat.commandDescription("avatar"))
            .addSubcommands(SubcommandData("guild", translationService.translate("command-avatar-subcommand-guild-name")))
            .addSubcommands(SubcommandData("user", translationService.translate("command-avatar-subcommand-user-name"))
                .addOptions(OptionData(OptionType.USER, "user", translationService.translate("command-avatar-option-user-name"))))
    }

    override fun execute(event: SlashCommandInteractionEvent) {
        when(event.subcommandName) {
            "guild" -> {
                event.guild?.let { guild ->
                    val embed = EmbedBuilder()
                        .setTitle(translationService.translate("command-avatar-message-guild-avatar"))
                        .setImage(guild.iconUrl)
                        .setColor(BotColors.PRIMARY)
                        .build()
                    event.replyEmbeds(embed).queue()
                }
            }
            "user" -> {
                val user = event.getOption("user")?.asUser
                val embed = EmbedBuilder()
                    .setTitle(translationService.translate("command-avatar-message-user-avatar", user?.name))
                    .setImage(user?.avatarUrl)
                    .setColor(BotColors.PRIMARY)
                    .build()
                event.replyEmbeds(embed).queue()
            }
        }
    }
}
