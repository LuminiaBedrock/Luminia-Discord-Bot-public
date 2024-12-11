package com.luminia.discord.bot.settings.option;

public class OptionLong extends Option<Long> {

    public OptionLong(String name) {
        super(name, 0L);
    }

    public OptionLong(String name, long defaultValue) {
        super(name, defaultValue);
    }

    public OptionLong(String name, String displayName, long defaultValue) {
        super(name, displayName, defaultValue);
    }

    @Override
    public OptionType getType() {
        return OptionType.LONG;
    }
}
