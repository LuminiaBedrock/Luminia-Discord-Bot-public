package com.luminia.discord.api.logger;

import com.luminia.discord.api.utils.TextFormat;
import lombok.Getter;

@Getter
public enum LogLevel {
    INFO("INFO ", TextFormat.YELLOW),
    WARN("WARN ", TextFormat.RED),
    ERROR("ERROR", TextFormat.RED),
    DEBUG("DEBUG", TextFormat.CYAN);

    private final String name;
    private final String color;

    LogLevel(String name, String color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return color + name + TextFormat.RESET;
    }
}
