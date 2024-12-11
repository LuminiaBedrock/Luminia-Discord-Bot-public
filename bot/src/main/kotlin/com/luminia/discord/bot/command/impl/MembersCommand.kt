package com.luminia.discord.bot.command.impl;

import com.luminia.discord.api.command.SlashCommand
import com.luminia.discord.api.command.data.CommandData
import com.luminia.discord.bot.service.translation.TranslationFormat
import com.luminia.discord.bot.service.translation.TranslationService
import com.luminia.discord.bot.utils.BotColors
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class MembersCommand : SlashCommand() {

    private val translationService = TranslationService.getInstance()

    override fun getCommandData(): CommandData {
        return CommandData.slash("members", TranslationFormat.commandDescription("members"))
    }

    override fun execute(event: SlashCommandInteractionEvent) {
        event.guild?.let { guild ->
            val roles = guild.roles.filter { role ->
                guild.getMembersWithRoles(role).isNotEmpty() && !role.tags.isBot
            }

            val builder = StringBuilder().append("```kt\n")
            roles.forEach { role ->
                builder.append(translationService.translate("command-members-success-members-with-role", role.name, guild.getMembersWithRoles(role).count()) + "\n")
            }
            builder.append("```")

            val embed = EmbedBuilder()
                .setTitle(translationService.translate("command-members-success-title"))
                .setDescription(builder.toString())
                .setFooter(translationService.translate("command-members-success-footer", guild.memberCount))
                .setColor(BotColors.PRIMARY)
                .build()
            event.replyEmbeds(embed).queue()
        }
    }
}
