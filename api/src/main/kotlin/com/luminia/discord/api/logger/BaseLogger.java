package com.luminia.discord.api.logger;

import com.luminia.discord.api.utils.TextFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseLogger {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS");

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void error(String message) {
        error(message, null);
    }

    public void error(String message, Throwable throwable) {
        log(LogLevel.ERROR, message, throwable);
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    private void log(LogLevel level, String message) {
        log(level, message, null);
    }

    private void log(LogLevel level, String message, Throwable throwable) {
        String date = DATE_FORMAT.format(new Date());
        System.out.println(TextFormat.MAGENTA + date + TextFormat.RESET + " [" + level + "]" + " " + message);
        if (throwable != null) {
            throwable.printStackTrace();
        }
    }
}