package com.luminia.discord.bot.utils.rcon.packet;

public interface RconPacketType {
    int AUTH_REQUEST = 3;
    int AUTH_RESPONSE = 2;
    int COMMAND_EXECUTE =2;
    int COMMAND_RESPONSE = 0;
}
