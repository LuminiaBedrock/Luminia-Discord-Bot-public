package com.luminia.discord.bot.handler.impl;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ModalInteractionHandler extends ListenerAdapter {

    private static final Map<String, Consumer<ModalInteractionEvent>> handlers = new HashMap<>();

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        Consumer<ModalInteractionEvent> handler = handlers.get(event.getModalId());
        if (handler != null) {
            handler.accept(event);
            handlers.remove(event.getModalId());
        }
    }

    public static void addHandler(String modalId, Consumer<ModalInteractionEvent> handler) {
        handlers.put(modalId, handler);
    }
}
