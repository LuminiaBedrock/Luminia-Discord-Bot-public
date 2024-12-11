package com.luminia.discord.bot.command.impl

import com.luminia.discord.api.command.SlashCommand
import com.luminia.discord.api.command.data.CommandData
import com.luminia.discord.bot.service.translation.TranslationFormat
import com.luminia.discord.bot.service.translation.TranslationService
import com.luminia.discord.bot.utils.BotColors
import com.luminia.discord.bot.utils.ConfigHelper
import com.luminia.discord.bot.utils.query.BedrockQuery
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class MinecraftServerCommand : SlashCommand() {

    private val translationService = TranslationService.getInstance()
    private val bedrockQuery = BedrockQuery()

    override fun getCommandData(): CommandData {
        return CommandData.slash("server", TranslationFormat.commandDescription("server"))
    }

    override fun execute(event: SlashCommandInteractionEvent) {
        val config = ConfigHelper.getConfig(ConfigHelper.CONFIG) ?: return

        val serverNode = config.node("minecraft-server-command")
        val address = serverNode.node("address").asString()
        val port = serverNode.node("port").asInt()

        bedrockQuery.query(address, port).thenAcceptAsync { response ->

            val builder = StringBuilder()
                .append(translationService.translate("command-server-embed-server-information") + "\n")
                .append(translationService.translate("command-server-embed-address", address) + "\n")
                .append(translationService.translate("command-server-embed-port", port) + "\n\n")

            if (response.online) {
                builder
                    .append(translationService.translate("command-server-embed-current-information") + "\n")
                    .append(translationService.translate("command-server-embed-player-count", response.playerCount, response.maxPlayers) + "\n")
                    .append(translationService.translate("command-server-embed-minecraft-version", response.minecraftVersion) + "\n")
            } else {
                builder.append(translationService.translate("command-server-embed-server-offline") + "\n")
            }

            builder.append("\n" + translationService.translate("command-server-embed-site"))

            val embed = EmbedBuilder()
                .setTitle(translationService.translate("command-server-embed-title"))
                .setDescription(builder.toString())
                .setImage(serverNode.node("image").asString())
                .setColor(BotColors.PRIMARY)

            event.replyEmbeds(embed.build()).queue()
        }

    }
}