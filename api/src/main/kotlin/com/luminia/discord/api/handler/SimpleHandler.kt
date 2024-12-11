package com.luminia.discord.api.handler

import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.hooks.EventListener
import java.util.function.Consumer

class SimpleHandler : EventListener {

    companion object {
        private val handlers: MutableMap<Class<*>, Consumer<GenericEvent>> = HashMap()

        @Suppress("unchecked_cast")
        @JvmStatic
        fun <T : GenericEvent> subscribe(eventType: Class<T>, callback: Consumer<T>) {
            handlers[eventType] = callback as Consumer<GenericEvent>
        }

        inline fun <reified T : GenericEvent> subscribe(crossinline action: (T) -> Unit) {
            subscribe(T::class.java) { event -> action(event) }
        }
    }

    override fun onEvent(event: GenericEvent) {
        val clazz = event.javaClass
        val callback = handlers[clazz]
        callback?.accept(event)
    }
}