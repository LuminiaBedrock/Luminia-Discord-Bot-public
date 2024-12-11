package com.luminia.discord.bot.handler.impl;

import com.luminia.discord.api.command.*;
import com.luminia.discord.bot.DiscordBot;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CommandInteractionHandler extends ListenerAdapter {

    private final DiscordBot discordBot = DiscordBot.getInstance();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        CommandManager commandManager = discordBot.getCommandManager();
        Command<?> command = commandManager.getCommand(CommandType.SLASH, event.getName());

        commandManager.execute(command, event);
    }

    @Override
    public void onMessageContextInteraction(@NotNull MessageContextInteractionEvent event) {
        CommandManager commandManager = discordBot.getCommandManager();
        Command<?> command = commandManager.getCommand(CommandType.MESSAGE, event.getName());

        commandManager.execute(command, event);
    }

    @Override
    public void onUserContextInteraction(@NotNull UserContextInteractionEvent event) {
        CommandManager commandManager = discordBot.getCommandManager();
        Command<?> command = commandManager.getCommand(CommandType.USER, event.getName());

        commandManager.execute(command, event);
    }
}
