package com.luminia.discord.bot.settings.option;

import java.util.function.Function;

public enum OptionType {
    BOOLEAN(Boolean::parseBoolean),
    STRING(value -> value),
    DOUBLE(Double::parseDouble),
    INTEGER(Integer::parseInt),
    LONG(Long::parseLong);

    private final Function<String, Object> function;

    OptionType(Function<String, Object> function) {
        this.function = function;
    }

    public Object parse(String value) {
        return function.apply(value);
    }
}
