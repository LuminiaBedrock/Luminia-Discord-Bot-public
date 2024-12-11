package com.luminia.discord.bot.command.impl;

import com.luminia.discord.api.command.SlashCommand
import com.luminia.discord.api.command.data.CommandData
import com.luminia.discord.bot.service.translation.TranslationFormat
import com.luminia.discord.bot.service.translation.TranslationService
import com.luminia.discord.bot.utils.extension.setHandler
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import net.dv8tion.jda.api.interactions.components.text.TextInput
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle
import net.dv8tion.jda.api.interactions.modals.Modal
import java.awt.Color

class MessageEmbedCommand : SlashCommand() {

    private val translationService = TranslationService.getInstance()

    override fun getCommandData(): CommandData {
        val optionRole = OptionData(OptionType.STRING, "message", translationService.translate("command-embed-option-message-name")).setRequired(false)
        return CommandData.slash("embed", TranslationFormat.commandDescription("embed"))
            .addSubcommands(
                SubcommandData("guild", translationService.translate("command-embed-subcommand-guild-name")).addOptions(optionRole),
                SubcommandData("user", translationService.translate("command-embed-subcommand-user-name")).addOptions(optionRole),
                SubcommandData("none", translationService.translate("command-embed-subcommand-none-name")).addOptions(optionRole)
            )
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MESSAGE_MANAGE))
    }

    override fun execute(event: SlashCommandInteractionEvent) {
        val subcommandName = event.subcommandName
        val message = event.getOption("message")?.asString

        val components = listOf(
            TextInput.create("title", translationService.translate("command-embed-modal-input-title-name"), TextInputStyle.SHORT)
                .setPlaceholder(translationService.translate("command-embed-modal-input-title-placeholder"))
                .setMaxLength(256)
                .build(),
            TextInput.create("description", translationService.translate("command-embed-modal-input-description-name"), TextInputStyle.PARAGRAPH)
                .setPlaceholder(translationService.translate("command-embed-modal-input-description-placeholder"))
                .setMaxLength(4000)
                .build(),
            TextInput.create("color", translationService.translate("command-embed-modal-input-color-name"), TextInputStyle.SHORT)
                .setPlaceholder(translationService.translate("command-embed-modal-input-color-placeholder"))
                .setRequired(false)
                .setMinLength(6)
                .setMaxLength(7)
                .build(),
            TextInput.create("image", translationService.translate("command-embed-modal-input-image-name"), TextInputStyle.SHORT)
                .setPlaceholder(translationService.translate("command-embed-modal-input-image-placeholder"))
                .setRequired(false)
                .build(),
            TextInput.create("footer", translationService.translate("command-embed-modal-input-footer-name"), TextInputStyle.SHORT)
                .setPlaceholder(translationService.translate("command-embed-modal-input-footer-placeholder"))
                .setMaxLength(512)
                .setRequired(false)
                .build()
        )

        val modal = Modal.create("embed", translationService.translate("command-embed-modal-title-name"))
            .apply {
                components.forEach {
                    addActionRow(it)
                }
            }
            .build()
            .setHandler { event ->
                val embed = EmbedBuilder()
                    .setTitle(event.getValue("title")?.asString)
                    .setDescription(event.getValue("description")?.asString)
                    .setFooter(event.getValue("footer")?.asString)
                    .setThumbnail(when(subcommandName) {
                        "guild" -> event.member!!.guild.iconUrl
                        "user" -> event.member!!.user.avatarUrl
                        else -> null
                    })

                event.getValue("color")?.asString.let {
                    embed.setColor(Color.decode(if (it!!.length < 6) "#9f70fd" else it))
                }
                event.getValue("image")?.asString.let {
                    if (!it.isNullOrEmpty()) embed.setImage(it)
                }

                event.channel.sendMessage(message ?: "")
                    .addEmbeds(embed.build())
                    .queue()

                event.reply(translationService.translate("command-embed-message-success"))
                    .setEphemeral(true)
                    .queue()
            }

        event.replyModal(modal).queue()
    }
}
