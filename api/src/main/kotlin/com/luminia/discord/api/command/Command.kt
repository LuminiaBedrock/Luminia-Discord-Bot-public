package com.luminia.discord.api.command

import com.luminia.discord.api.command.data.CommandData
import net.dv8tion.jda.api.events.Event

abstract class Command<T : Event> {

    abstract fun getCommandData(): CommandData

    abstract fun getCommandType(): CommandType

    abstract fun execute(event: T)
}