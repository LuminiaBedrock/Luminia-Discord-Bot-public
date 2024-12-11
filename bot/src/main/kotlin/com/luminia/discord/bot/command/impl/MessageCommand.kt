package com.luminia.discord.bot.command.impl

import com.luminia.discord.api.command.SlashCommand
import com.luminia.discord.api.command.data.CommandData
import com.luminia.discord.bot.service.translation.TranslationFormat
import com.luminia.discord.bot.service.translation.TranslationService
import com.luminia.discord.bot.utils.BotUtils
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData

class MessageCommand : SlashCommand() {

    private val translationService = TranslationService.getInstance()

    override fun getCommandData(): CommandData {
        return CommandData.slash("message", TranslationFormat.commandDescription("message"))
            .addOptions(
                OptionData(OptionType.STRING, "message", translationService.translate("command-message-option-message-name")),
                OptionData(OptionType.ATTACHMENT, "attachment", translationService.translate("command-message-option-attachment-name"))
                    .setRequired(false)
            )
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MESSAGE_MANAGE))
    }

    override fun execute(event: SlashCommandInteractionEvent) {
        val message = event.getOption("message")?.asString
        val attachment = event.getOption("attachment")?.asAttachment

        event.channel.sendMessage(message ?: "").apply {
            if (attachment != null) {
                addFiles(BotUtils.getFileUploadFromAttachment(attachment))
            }
        }.queue {
            event.reply(translationService.translate("command-message-message-success"))
                .setEphemeral(true)
                .queue()
        }
    }

}