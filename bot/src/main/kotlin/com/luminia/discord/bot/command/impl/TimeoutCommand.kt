package com.luminia.discord.bot.command.impl;

import com.luminia.discord.api.command.SlashCommand
import com.luminia.discord.api.command.data.CommandData
import com.luminia.discord.bot.service.translation.TranslationFormat
import com.luminia.discord.bot.service.translation.TranslationService
import com.luminia.discord.bot.utils.TimeFormatter
import com.luminia.discord.bot.utils.TimeParser
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import java.time.Duration
import java.util.concurrent.TimeUnit

class TimeoutCommand : SlashCommand() {

    private val translationService = TranslationService.getInstance()

    override fun getCommandData(): CommandData {
        val options = listOf(
            OptionData(OptionType.USER, "user", translationService.translate("command-timeout-option-user-name")),
            OptionData(OptionType.STRING, "duration", translationService.translate("command-timeout-option-duration-name"))
        )
        return CommandData.slash("timeout", TranslationFormat.commandDescription("timeout"))
            .addSubcommands(SubcommandData("add", translationService.translate("command-timeout-subcommand-add-name")).addOptions(options))
            .addSubcommands(SubcommandData("remove", translationService.translate("command-timeout-subcommand-remove-name")).addOptions(options[0]))
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MODERATE_MEMBERS))
    }

    override fun execute(event: SlashCommandInteractionEvent) {
        val user = event.getOption("user")?.asMember ?: return

        when(event.subcommandName) {
            "add" -> {
                val duration = TimeParser.parse(event.getOption("duration")?.asString)
                if (duration == null) {
                    event.reply(translationService.translate("command-timeout-message-invalid-duration")).setEphemeral(true).queue()
                    return
                }

                if (duration > TimeUnit.DAYS.toMillis(28)) {
                    event.reply(translationService.translate("command-timeout-message-duration-limit")).setEphemeral(true).queue()
                    return
                }

                user.timeoutFor(Duration.ofMillis(duration)).queue()
                event.reply(translationService.translate("command-timeout-message-timeout-added", user.effectiveName, TimeFormatter.format(duration))).queue()
            }
            "remove" -> {
                user.removeTimeout().queue()
                event.reply(translationService.translate("command-timeout-message-timeout-removed", user.effectiveName)).queue()
            }
        }
    }
}
