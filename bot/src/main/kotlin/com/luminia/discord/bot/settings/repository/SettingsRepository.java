package com.luminia.discord.bot.settings.repository;

import com.luminia.discord.bot.DiscordBot;
import com.luminia.discord.bot.settings.option.Option;

import java.util.concurrent.CompletableFuture;

public interface SettingsRepository {

    <T> CompletableFuture<Void> setOption(long id, Option<T> option, Object value);

    <T> CompletableFuture<T> getOption(long id, Option<T> option);

    <T> CompletableFuture<T> getOptionOrDefault(long id, Option<T> option, T defaultValue);

    static SettingsRepository getInstance() {
        return DiscordBot.getInstance().getSettingsRepository();
    }
}
