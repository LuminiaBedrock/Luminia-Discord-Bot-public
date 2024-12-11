package com.luminia.discord.bot.handler.impl;

import com.luminia.config.Config;
import com.luminia.discord.bot.service.translation.TranslationService;
import com.luminia.discord.bot.utils.BotEmoji;
import com.luminia.discord.bot.utils.ConfigHelper;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class GuildMemberJoinHandler extends ListenerAdapter {

    private final Config config = ConfigHelper.getConfig(ConfigHelper.CONFIG);

    private boolean isEnabled(String type) {
        return config.nodes("welcome-messages-channel." + type).asBoolean();
    }

    private long getChannelId() {
        return config.nodes("welcome-messages-channel.channel-id").asLong();
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        if (!this.isEnabled("enable-join")) {
            return;
        }

        TextChannel channel = event.getGuild().getTextChannelById(this.getChannelId());
        if (channel != null) {
            channel.sendMessage(TranslationService.getInstance().translate("generic-join-message",
                    BotEmoji.ARROW_RIGHT_GREEN.getFormatted(),
                    event.getMember().getEffectiveName(),
                    event.getMember().getAsMention())).queue();
        }
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        if (!this.isEnabled("enable-leave")) {
            return;
        }

        TextChannel channel = event.getGuild().getTextChannelById(this.getChannelId());
        if (channel != null) {
            channel.sendMessage(TranslationService.getInstance().translate("generic-leave-message",
                    BotEmoji.ARROW_LEFT_RED.getFormatted(),
                    event.getMember().getEffectiveName(),
                    event.getMember().getAsMention())).queue();
        }
    }
}
