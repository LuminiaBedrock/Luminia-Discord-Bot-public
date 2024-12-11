package com.luminia.discord.bot.settings.option;

public class OptionString extends Option<String>{

    public OptionString(String name) {
        super(name, "");
    }

    public OptionString(String name, String defaultValue) {
        super(name, defaultValue);
    }

    public OptionString(String name, String displayName, String defaultValue) {
        super(name, displayName, defaultValue);
    }

    @Override
    public OptionType getType() {
        return OptionType.STRING;
    }
}
