package com.luminia.discord.api.command

import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent

abstract class UserCommand : Command<UserContextInteractionEvent>() {

    override fun getCommandType(): CommandType {
        return CommandType.USER
    }
}