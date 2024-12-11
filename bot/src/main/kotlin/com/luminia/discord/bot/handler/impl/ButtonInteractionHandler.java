package com.luminia.discord.bot.handler.impl;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ButtonInteractionHandler extends ListenerAdapter {

    private static final Map<String, Consumer<ButtonInteractionEvent>> handlers = new HashMap<>();

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId();
        Consumer<ButtonInteractionEvent> handler = handlers.get(buttonId);
        if (handler != null) {
            handler.accept(event);
        }
    }

    public static void addHandler(String buttonId, Consumer<ButtonInteractionEvent> handler) {
        handlers.put(buttonId, handler);
    }
}
