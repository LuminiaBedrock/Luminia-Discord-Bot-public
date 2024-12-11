package com.luminia.discord.bot.utils;

import com.luminia.discord.bot.service.translation.TranslationService;

import java.util.concurrent.TimeUnit;

public class TimeFormatter {

    private static final String[] TIME_FORMS = {
            "time-form-day",
            "time-form-days",
            "time-form-many-days",
            "time-form-hour",
            "time-form-hours",
            "time-form-many-hours",
            "time-form-minute",
            "time-form-minutes",
            "time-form-many-minutes",
            "time-form-second",
            "time-form-seconds",
            "time-form-many-seconds"
    };

    /**
     * Get time form
     */
    private static String form(int form) {
        return TranslationService.getInstance().translate(TIME_FORMS[form]);
    }

    /**
     * Formats the time in milliseconds into a string representation.
     * @param millis Time in milliseconds
     * @return String representation of the formatted time
     */
    public static String format(long millis) {
        if (millis < 1000) {
            return 0 + " " + form(11);
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        long hours = TimeUnit.MILLISECONDS.toHours(millis) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;

        StringBuilder formatted = new StringBuilder();
        appendTimeUnit(formatted, days, form(0), form(1), form(2));
        appendTimeUnit(formatted, hours, form(3), form(4), form(5));
        appendTimeUnit(formatted, minutes, form(6), form(7), form(8));
        appendTimeUnit(formatted, seconds, form(9), form(10), form(11));

        return formatted.toString().trim();
    }

    private static void appendTimeUnit(StringBuilder builder, long value, String form1, String form2, String form5) {
        if (value > 0) {
            builder.append(value).append(" ").append(getNounForm(value, form1, form2, form5)).append(" ");
        }
    }

    public static String getNounForm(long number, String form1, String form2, String form5) {
        long absNumber = Math.abs(number);
        if (absNumber % 100 >= 11 && absNumber % 100 <= 19) {
            return form5;
        }
        if (absNumber % 10 == 1) {
            return form1;
        }
        if (absNumber % 10 >= 2 && absNumber % 10 <= 4) {
            return form2;
        }
        return form5;
    }
}