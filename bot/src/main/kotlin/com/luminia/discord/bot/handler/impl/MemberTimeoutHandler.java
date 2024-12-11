package com.luminia.discord.bot.handler.impl;

import com.luminia.discord.bot.service.translation.TranslationService;
import com.luminia.discord.bot.utils.BotColors;
import com.luminia.discord.bot.utils.TimeFormatter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateTimeOutEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MemberTimeoutHandler extends ListenerAdapter {

    @Override
    public void onGuildMemberUpdateTimeOut(@NotNull GuildMemberUpdateTimeOutEvent event) {
        TranslationService translationService = TranslationService.getInstance();

        Guild guild = event.getGuild();
        User user = event.getMember().getUser();

        if (event.getGuild().getSelfMember().getUser().equals(user)) {
            return;
        }

        user.openPrivateChannel().queue(channel -> {
            if (!channel.canTalk()) {
                return;
            }

            EmbedBuilder embed = new EmbedBuilder().setTitle(translationService.translate("timeout-embed-title"));

            if (event.getNewTimeOutEnd() == null) {
                embed
                    .setDescription(translationService.translate("timeout-embed-description-remove", guild.getName()))
                    .setColor(BotColors.SUCCESS);
            } else {
                long duration = event.getNewTimeOutEnd().toInstant().toEpochMilli() - System.currentTimeMillis();
                embed
                    .setDescription(translationService.translate("timeout-embed-description-add", TimeFormatter.format(duration), guild.getName()))
                    .setColor(BotColors.WARNING);
            }

            channel.sendMessageEmbeds(embed.build()).queue();
        });
    }
}
