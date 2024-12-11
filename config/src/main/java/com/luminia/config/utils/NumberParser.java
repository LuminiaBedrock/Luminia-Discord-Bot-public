package com.luminia.config.utils;

public class NumberParser {

    public static Integer parseInteger(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Long parseLong(String string) {
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Double parseDouble(String string) {
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Float parseFloat(String string) {
        try {
            return Float.parseFloat(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

