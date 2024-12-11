package com.luminia.discord.bot.command.impl;

import com.luminia.discord.api.command.SlashCommand
import com.luminia.discord.api.command.data.CommandData
import com.luminia.discord.bot.service.translation.TranslationFormat
import com.luminia.discord.bot.service.translation.TranslationService
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData

class SetNameCommand : SlashCommand() {

    private val translationService = TranslationService.getInstance()

    override fun getCommandData(): CommandData {
        return CommandData.slash("setname", TranslationFormat.commandDescription("setname"))
            .addOptions(
                OptionData(OptionType.USER, "user", translationService.translate("command-setname-option-user-name")),
                OptionData(OptionType.STRING, "name", translationService.translate("command-setname-option-name-name"))
            )
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.NICKNAME_CHANGE))
    }

    override fun execute(event: SlashCommandInteractionEvent) {
        val user = event.getOption("user")?.asMember?: return
        val name = event.getOption("name")?.asString

        if (user.canInteract(user)) {
            user.modifyNickname(name).queue()
            event.reply(translationService.translate("command-setname-message-changed", user.effectiveName, name)).queue()
        } else {
            event.reply(translationService.translate("generic-can-not-interact")).setEphemeral(true).queue()
        }
    }
}
