package com.luminia.discord.bot.settings.option;

public interface OptionTypes {

    OptionLong RCON_ROLE = new OptionLong("rcon_role", "Rcon Role ID", 0L);
    OptionString RCON_ADDRESS = new OptionString("rcon_address", "Rcon Role ID", null);
    OptionInteger RCON_PORT = new OptionInteger("rcon_port", "Rcon Role ID", 19132);
    OptionString RCON_PASSWORD = new OptionString("rcon_password", "Rcon Role ID", null);
}
