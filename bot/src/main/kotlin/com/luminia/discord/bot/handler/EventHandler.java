package com.luminia.discord.bot.handler;

import com.luminia.discord.bot.DiscordBot;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public class EventHandler implements EventListener {

    private final DiscordBot discordBot = DiscordBot.getInstance();

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        discordBot.getEventManager().handle(event);
    }
}
