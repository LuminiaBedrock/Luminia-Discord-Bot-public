package com.luminia.discord.bot.settings.option;

public class OptionDouble extends Option<Double> {

    public OptionDouble(String name) {
        super(name, 0D);
    }

    public OptionDouble(String name, double defaultValue) {
        super(name, defaultValue);
    }

    public OptionDouble(String name, String displayName, double defaultValue) {
        super(name, displayName, defaultValue);
    }

    @Override
    public OptionType getType() {
        return OptionType.DOUBLE;
    }
}
