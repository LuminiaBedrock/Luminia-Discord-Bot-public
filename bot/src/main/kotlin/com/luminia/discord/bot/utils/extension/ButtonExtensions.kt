package com.luminia.discord.bot.utils.extension

import com.luminia.discord.bot.handler.impl.ButtonInteractionHandler
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.interactions.components.buttons.Button

import java.util.function.Consumer

fun Button.setHandler(handler: Consumer<ButtonInteractionEvent>): Button {
    ButtonInteractionHandler.addHandler(this.id, handler)
    return this
}