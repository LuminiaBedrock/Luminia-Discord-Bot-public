package com.luminia.discord.api.command

import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent

abstract class MessageCommand : Command<MessageContextInteractionEvent>() {

    override fun getCommandType(): CommandType {
        return CommandType.MESSAGE
    }
}