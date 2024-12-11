package com.luminia.discord.api.utils;

public interface TextFormat {
    String BLACK = "\u001b[0;30m";
    String RED = "\u001b[0;31m";
    String GREEN = "\u001b[0;92m";
    String YELLOW = "\u001b[0;33m";
    String BLUE = "\u001b[0;34m";
    String MAGENTA = "\u001b[0;35m";
    String CYAN = "\u001b[0;36m";
    String WHITE = "\u001b[0;37m";

    String BLACK_BACKGROUND = "\u001b[40m";
    String RED_BACKGROUND = "\u001b[41m";
    String GREEN_BACKGROUND = "\u001b[42m";
    String YELLOW_BACKGROUND = "\u001b[43m";
    String BLUE_BACKGROUND = "\u001b[44m";
    String MAGENTA_BACKGROUND = "\u001b[45m";
    String CYAN_BACKGROUND = "\u001b[46m";
    String WHITE_BACKGROUND = "\u001b[47m";

    String RESET = "\u001b[0m";
    String BOLD = "\u001b[1m";
    String UNDERLINE = "\u001b[4m";
    String REVERSED = "\u001b[7m";
}