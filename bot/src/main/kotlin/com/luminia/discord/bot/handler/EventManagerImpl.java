package com.luminia.discord.bot.handler;

import com.luminia.discord.api.handler.EventManager;
import com.luminia.discord.api.handler.Subscribe;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class EventManagerImpl implements EventManager {

    private final JDA jda;
    private final Map<Class<? extends GenericEvent>, Set<Consumer<GenericEvent>>> handlers;

    public EventManagerImpl(JDA jda) {
        this.jda = jda;
        this.handlers = new HashMap<>();
    }

    @Override
    public Map<Class<? extends GenericEvent>, Set<Consumer<GenericEvent>>> getHandlers() {
        return handlers;
    }

    @Override
    public void register(Object... listeners) {
        for (Object listener : listeners) {

            if (listener instanceof EventListener) {
                jda.addEventListener(listener);
                continue;
            }

            for (Method method : listener.getClass().getDeclaredMethods()) {
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if (subscribe == null || method.getParameterCount() != 1) {
                    continue;
                }

                Class<?>[] parameterTypes = method.getParameterTypes();
                if (!GenericEvent.class.isAssignableFrom(parameterTypes[0])) {
                    continue;
                }

                @SuppressWarnings("unchecked")
                Class<? extends GenericEvent> eventClass = (Class<? extends GenericEvent>) parameterTypes[0];

                Consumer<GenericEvent> callback = event -> {
                    try {
                        method.invoke(listener, event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };

                handlers.computeIfAbsent(eventClass, e -> new HashSet<>()).add(callback);
            }
        }
    }

    @Override
    public void handle(GenericEvent event) {
        if (handlers.containsKey(event.getClass())) {
            handlers.get(event.getClass()).forEach(handler -> handler.accept(event));
        }
    }
}
