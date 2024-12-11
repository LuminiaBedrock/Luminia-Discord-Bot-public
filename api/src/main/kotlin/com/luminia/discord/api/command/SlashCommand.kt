package com.luminia.discord.api.command

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

abstract class SlashCommand : Command<SlashCommandInteractionEvent>() {

    override fun getCommandType(): CommandType {
        return CommandType.SLASH
    }
}