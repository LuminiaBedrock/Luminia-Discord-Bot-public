package com.luminia.discord.bot.settings.option;

public class OptionInteger extends Option<Integer> {

    public OptionInteger(String name) {
        super(name, 0);
    }

    public OptionInteger(String name, int defaultValue) {
        super(name, defaultValue);
    }

    public OptionInteger(String name, String displayName, int defaultValue) {
        super(name, displayName, defaultValue);
    }

    @Override
    public OptionType getType() {
        return OptionType.INTEGER;
    }
}
