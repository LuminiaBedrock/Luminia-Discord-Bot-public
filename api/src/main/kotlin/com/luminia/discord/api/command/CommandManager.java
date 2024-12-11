package com.luminia.discord.api.command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;

import java.util.Collections;
import java.util.Map;

public interface CommandManager {

    JDA getJDA();

    Map<CommandType, Map<String, Command<?>>> getCommands();

    default Command<?> getCommand(CommandType type, String name) {
        return this.getCommands().getOrDefault(type, Collections.emptyMap()).get(name);
    }

    void register(Command<?>... command);

    void execute(Command<?> command, GenericCommandInteractionEvent event);
}
