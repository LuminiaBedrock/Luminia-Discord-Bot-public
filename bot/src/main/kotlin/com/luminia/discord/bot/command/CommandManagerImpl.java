package com.luminia.discord.bot.command;

import com.luminia.discord.api.command.*;
import com.luminia.discord.api.command.permission.CommandPermission;
import com.luminia.discord.bot.logger.Logger;
import com.luminia.discord.bot.service.translation.TranslationService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;

import java.util.*;

public class CommandManagerImpl implements CommandManager {

    private final JDA jda;
    private final Map<CommandType, Map<String, Command<?>>> commands;
    private final TranslationService translationService;

    public CommandManagerImpl(JDA jda) {
        this.jda = jda;
        this.commands = new HashMap<>();
        this.translationService = TranslationService.getInstance();
    }

    @Override
    public JDA getJDA() {
        return jda;
    }

    @Override
    public Map<CommandType, Map<String, Command<?>>> getCommands() {
        return commands;
    }

    @Override
    public void register(Command<?>... commands) {
        Arrays.stream(commands)
                .forEach(command -> {
                    this.commands.computeIfAbsent(command.getCommandType(), c -> new HashMap<>())
                            .put(command.getCommandData().getJDACommandData().getName(), command);
                });

        jda.updateCommands()
                .addCommands(Arrays.stream(commands)
                        .map(command -> command.getCommandData().getJDACommandData())
                        .toList())
                .queue();
    }

    @Override
    public void execute(Command<?> command, GenericCommandInteractionEvent event) {
        CommandPermission permission = command.getCommandData().getPermission();
        Member member = event.getMember();

        if (member != null) {
            if (!permission.test(member)) {
                event.reply(translationService.translate("generic-no-permission")).setEphemeral(true).queue();
                return;
            }
        } else {
            if (!permission.defaultValue()) {
                event.reply(translationService.translate("generic-no-permission")).setEphemeral(true).queue();
                return;
            }
        }

        try {
            if (command instanceof SlashCommand slashCommand && event instanceof  SlashCommandInteractionEvent slashEvent) {
                slashCommand.execute(slashEvent);
                return;
            }
            if (command instanceof MessageCommand messageCommand && event instanceof MessageContextInteractionEvent messageEvent) {
                messageCommand.execute(messageEvent);
                return;
            }
            if (command instanceof UserCommand messageCommand && event instanceof UserContextInteractionEvent userEvent) {
                messageCommand.execute(userEvent);
            }
        } catch (Exception exception) {
            Logger.getInstance().error("An error occurred while executing the command", exception);
            event.reply(translationService.translate("generic-command-error", exception.getMessage()))
                    .setEphemeral(true)
                    .queue();
        }
    }
}
