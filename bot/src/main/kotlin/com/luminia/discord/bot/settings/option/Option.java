package com.luminia.discord.bot.settings.option;

import lombok.Getter;

@Getter
public abstract class Option<T> {

    private final String name;
    private final String displayName;
    private final T defaultValue;

    public Option(String name, T defaultValue) {
        this(name, name, defaultValue);
    }

    public Option(String name, String displayName, T defaultValue) {
        this.name = name;
        this.displayName = displayName;
        this.defaultValue = defaultValue;
    }

    /**
     * Get option type
     */
    public abstract OptionType getType();

    /**
     * Get object as option type
     */
    @SuppressWarnings("unchecked")
    public T asType(String value) {
        return (T) this.getType().parse(value);
    }
}
