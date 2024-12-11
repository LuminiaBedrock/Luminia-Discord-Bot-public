package com.luminia.discord.bot.logger;

import com.luminia.discord.api.logger.BaseLogger;
import lombok.Getter;

public class Logger extends BaseLogger {

    @Getter
    private static final Logger instance = new Logger();
}
