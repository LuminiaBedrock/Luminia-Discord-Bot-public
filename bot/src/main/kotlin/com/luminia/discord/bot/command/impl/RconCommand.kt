package com.luminia.discord.bot.command.impl;

import com.luminia.discord.api.command.SlashCommand
import com.luminia.discord.api.command.data.CommandData
import com.luminia.discord.bot.service.translation.TranslationFormat
import com.luminia.discord.bot.service.translation.TranslationService
import com.luminia.discord.bot.settings.option.OptionTypes
import com.luminia.discord.bot.settings.repository.SettingsRepository
import com.luminia.discord.bot.utils.BotColors
import com.luminia.discord.bot.utils.extension.setHandler
import com.luminia.discord.bot.utils.rcon.RconClient
import com.luminia.discord.bot.utils.rcon.exception.AuthenticationException
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle
import kotlin.concurrent.thread

class RconCommand : SlashCommand() {

    private val translationService = TranslationService.getInstance()
    private val settingsRepository = SettingsRepository.getInstance();

    override fun getCommandData(): CommandData {
        return CommandData.slash("rcon", TranslationFormat.commandDescription("rcon"))
            .addSubcommands(
                SubcommandData("command", TranslationFormat.commandSubcommand("rcon", "command"))
                    .addOptions(
                        OptionData(OptionType.STRING, "command", TranslationFormat.commandOption("rcon", "command"))
                    ),
                SubcommandData("setrole", TranslationFormat.commandSubcommand("rcon", "setrole"))
                    .addOptions(
                        OptionData(OptionType.ROLE, "role", TranslationFormat.commandOption("rcon", "role"))
                    ),
                SubcommandData("setrcon", TranslationFormat.commandSubcommand("rcon", "setrcon"))
                    .addOptions(
                        OptionData(OptionType.STRING, "address", TranslationFormat.commandOption("rcon", "address")),
                        OptionData(OptionType.INTEGER, "port", TranslationFormat.commandOption("rcon", "port")),
                        OptionData(OptionType.STRING, "password", TranslationFormat.commandOption("rcon", "password"))
                    )
            )
            .setGuildOnly(true)
    }

    override fun execute(event: SlashCommandInteractionEvent) {
        val guild = event.guild ?: return
        when(event.subcommandName) {
            "setrole" -> handleSetRole(event, guild)
            "setrcon" -> handleSetRcon(event, guild)
            "command" -> handleCommand(event, guild)
        }
    }

    private fun handleSetRole(event: SlashCommandInteractionEvent, guild: Guild) {
        if (!event.member!!.hasPermission(Permission.ADMINISTRATOR)) {
            event.reply(translationService.translate("generic-no-permission")).setEphemeral(true).queue()
            return
        }

        val role = event.getOption("role")?.asRole ?: return

        settingsRepository.setOption(guild.idLong, OptionTypes.RCON_ROLE, role.idLong).join()

        event.reply(translationService.translate("command-rcon-message-role-set", role.name))
            .setEphemeral(true)
            .queue()
    }

    private fun handleSetRcon(event: SlashCommandInteractionEvent, guild: Guild) {
        if (!event.member!!.hasPermission(Permission.ADMINISTRATOR)) {
            event.reply(translationService.translate("generic-no-permission")).setEphemeral(true).queue()
            return
        }

        val address = event.getOption("address")?.asString ?: return
        val port = event.getOption("port")?.asInt ?: return
        val password = event.getOption("password")?.asString ?: return

        settingsRepository.setOption(guild.idLong, OptionTypes.RCON_ADDRESS, address)
        settingsRepository.setOption(guild.idLong, OptionTypes.RCON_PORT, port)
        settingsRepository.setOption(guild.idLong, OptionTypes.RCON_PASSWORD, password)

        event.reply(translationService.translate("command-rcon-message-rcon-set"))
            .setEphemeral(true)
            .queue()
    }

    private fun handleCommand(event: SlashCommandInteractionEvent, guild: Guild) {
        val role = guild.getRoleById(settingsRepository.getOption(guild.idLong, OptionTypes.RCON_ROLE).join())
        if (role == null) {
            event.reply(translationService.translate("command-rcon-message-role-not-found")).setEphemeral(true).queue()
            return
        }

        if (!event.member!!.roles.contains(role)) {
            event.reply(translationService.translate("generic-no-permission")).setEphemeral(true).queue()
            return
        }

        val command = event.getOption("command")?.asString

        val address = settingsRepository.getOption(guild.idLong, OptionTypes.RCON_ADDRESS).join()
        val port = settingsRepository.getOption(guild.idLong, OptionTypes.RCON_PORT).join()
        val password = settingsRepository.getOption(guild.idLong, OptionTypes.RCON_PASSWORD).join()

        if (address == null || port == null || password == null) {
            event.reply(translationService.translate("command-rcon-message-rcon-not-set")).setEphemeral(true).queue()
            return
        }

        thread(start = true) {
            val rcon = RconClient(address, port)

            try {
                rcon.connect(password)
            } catch (exception: AuthenticationException) {
                if (exception.message == "Server is offline") {
                    event.reply(translationService.translate("command-rcon-message-server-offline")).queue()
                } else {
                    event.reply(translationService.translate("command-rcon-message-auth-error", exception.message)).queue()
                    exception.printStackTrace()
                }
                return@thread
            }

            sendCommand(event, rcon, command)
        }
    }

    private fun sendCommand(genericEvent: GenericInteractionCreateEvent, rcon: RconClient, command: String?) {
        val response = rcon.sendCommand(command)
        val embed = EmbedBuilder()
            .setTitle(translationService.translate("command-rcon-embed-title"))
            .setDescription(response.ifBlank { translationService.translate("command-rcon-embed-response-empty") })
            .setColor(BotColors.PRIMARY)
            .build()

        val button = Button.primary("resend", translationService.translate("command-rcon-button-resend"))
            .withStyle(ButtonStyle.SECONDARY)
            .setHandler {
                val guild = it.guild ?: return@setHandler

                val role = guild.getRoleById(settingsRepository.getOption(guild.idLong, OptionTypes.RCON_ROLE).join())
                if (role == null) {
                    it.reply(translationService.translate("command-rcon-message-role-not-found")).setEphemeral(true).queue()
                    return@setHandler
                }

                if (!it.member!!.roles.contains(role)) {
                    it.reply(translationService.translate("generic-no-permission")).setEphemeral(true).queue()
                    return@setHandler
                }

                sendCommand(it, rcon, command)
            }

        val event = when(genericEvent) {
            is SlashCommandInteractionEvent -> genericEvent
            is ButtonInteractionEvent -> genericEvent
            else -> return
        }
        event.replyEmbeds(embed).addActionRow(button).queue()
    }
}
