package com.luminia.discord.api.handler;

import net.dv8tion.jda.api.events.GenericEvent;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public interface EventManager {

    Map<Class<? extends GenericEvent>, Set<Consumer<GenericEvent>>> getHandlers();

    void register(Object... listeners);

    void handle(GenericEvent event);
}
