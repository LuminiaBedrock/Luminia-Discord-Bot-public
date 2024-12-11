package com.luminia.discord.bot.utils;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeParser {

    private static final Pattern TIME_PATTERN = Pattern.compile("(\\d+)(mo|[smhdwy])");
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

    public static Long parse(String timeString) {
        Matcher matcher = TIME_PATTERN.matcher(timeString.trim().toLowerCase());

        if (!matcher.find()) {
            return null;
        }

        Long number = Long.parseLong(matcher.group(1));
        String unit = matcher.group(2);

        return switch(unit) {
            case "s" -> TimeUnit.SECONDS.toMillis(number);
            case "m" -> TimeUnit.MINUTES.toMillis(number);
            case "h" -> TimeUnit.HOURS.toMillis(number);
            case "d" -> TimeUnit.DAYS.toMillis(number);
            case "w" -> TimeUnit.DAYS.toMillis(7 * number);
            case "mo" -> TimeUnit.DAYS.toMillis(30 * number);
            case "y" -> TimeUnit.DAYS.toMillis(365 * number);
            default -> DEFAULT_TIME_UNIT.toMillis(number);
        };
    }

    public static boolean canParse(String timeString) {
        return TIME_PATTERN.matcher(timeString.trim().toLowerCase()).matches();
    }
}
