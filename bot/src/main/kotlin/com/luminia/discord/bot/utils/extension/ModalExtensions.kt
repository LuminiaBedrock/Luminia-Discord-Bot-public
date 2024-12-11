package com.luminia.discord.bot.utils.extension

import com.luminia.discord.bot.handler.impl.ModalInteractionHandler
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.api.interactions.modals.Modal

import java.util.function.Consumer

fun Modal.setHandler(handler: Consumer<ModalInteractionEvent>): Modal {
    ModalInteractionHandler.addHandler(this.id, handler)
    return this
}