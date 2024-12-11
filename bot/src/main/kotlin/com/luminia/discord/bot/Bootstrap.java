package com.luminia.discord.bot;

import com.luminia.config.Config;
import com.luminia.discord.bot.logger.Logger;
import com.luminia.discord.bot.utils.ConfigHelper;

import java.io.File;
import java.util.List;

public class Bootstrap {

    private static final Logger logger = Logger.getInstance();

    public static void main(String[] args) {
        logger.info("Starting the bot...");

        List<String> resources = List.of(
                ConfigHelper.CONFIG
        );

        for (String name : resources) {
            File file = new File(name);
            if (!file.exists()) {
                ConfigHelper.saveResource(name, false);
                logger.info("Resource \"" + name + "\" saved.");
            }
            ConfigHelper.loadConfig(name);
        }

        Config config = ConfigHelper.getConfig(ConfigHelper.CONFIG);
        if (config == null) {
            logger.error("Failed to start bot. File config.yml not found.");
            return;
        }

        try {
            new DiscordBot(config.nodes("discord.token").asString(), config);
        } catch (Exception e) {
            logger.error("Failed to start the bot", e);
            return;
        }

        logger.info("Bot is started!");
    }
}

