package com.luminia.discord.bot.command.impl

import com.luminia.discord.api.command.SlashCommand
import com.luminia.discord.api.command.data.CommandData
import com.luminia.discord.bot.service.translation.TranslationFormat
import com.luminia.discord.bot.service.translation.TranslationService
import com.luminia.discord.bot.utils.BotColors
import com.luminia.discord.bot.utils.query.BedrockQuery
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import java.net.InetAddress

class MinecraftStatusCommand : SlashCommand() {

    private val translationService = TranslationService.getInstance()
    private val bedrockQuery = BedrockQuery()

    override fun getCommandData(): CommandData {
        return CommandData.slash("status", TranslationFormat.commandDescription("status"))
            .addOptions(
                OptionData(OptionType.STRING, "address", translationService.translate("command-status-option-address-name")),
                OptionData(OptionType.INTEGER, "port", translationService.translate("command-status-option-port-name"))
            )
    }

    override fun execute(event: SlashCommandInteractionEvent) {
        val options = event.options

        if (options.isEmpty()) {
            event.reply(translationService.translate("command-status-message-no-address-given")).setEphemeral(true).queue()
            return
        }

        val address = options[0].asString
        val port = options.getOrNull(1)?.asInt ?: 19132

        bedrockQuery.query(address, port).thenAcceptAsync { response ->

            val embed = EmbedBuilder()
                .setTitle(translationService.translate("command-status-embed-title", address, port))
                .setColor(BotColors.PRIMARY)

            println(response.minecraftVersion)
            val builder = StringBuilder()
                .append(translationService.translate("command-status-embed-motd", response.motd) + "\n\n")
                .append(translationService.translate("command-status-embed-player-count", response.playerCount, response.maxPlayers) + "\n")
                .append(translationService.translate("command-status-embed-minecraft-version", response.minecraftVersion, response.protocolVersion) + "\n")
                .append(translationService.translate("command-status-embed-gamemode", response.gamemode) + "\n\n")
                .append(translationService.translate("command-status-embed-software", response.software) + "\n\n")
                .append(translationService.translate("command-status-embed-additional") + "\n")
                .append(translationService.translate("command-status-embed-address", InetAddress.getByName(address).hostAddress))

            if (response.online) {
                embed.setDescription(builder.toString())
            } else {
                embed.setDescription(translationService.translate("command-status-embed-server-offline"))
            }

            event.replyEmbeds(embed.build()).setEphemeral(true).queue()
        }
    }
}