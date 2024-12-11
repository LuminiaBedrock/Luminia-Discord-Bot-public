package com.luminia.discord.bot.settings.option;

public class OptionBoolean extends Option<Boolean> {

    public OptionBoolean(String name) {
        super(name, false);
    }

    public OptionBoolean(String name, boolean defaultValue) {
        super(name, defaultValue);
    }

    public OptionBoolean(String name, String displayName, boolean defaultValue) {
        super(name, displayName, defaultValue);
    }

    @Override
    public OptionType getType() {
        return OptionType.BOOLEAN;
    }
}
