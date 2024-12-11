package com.luminia.discord.api.command.data

import com.luminia.discord.api.command.permission.CommandPermission
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.CommandData as JDACommandData

interface CommandData {

    fun getJDACommandData(): JDACommandData

    fun getPermission(): CommandPermission

    fun setName(name: String): CommandData

    fun setDefaultPermissions(permission: DefaultMemberPermissions): CommandData

    fun setPermission(permission: CommandPermission): CommandData

    fun setGuildOnly(guildOnly: Boolean): CommandData

    fun setNSFW(nsfw: Boolean): CommandData

    companion object {

        fun slash(name: String, description: String): SlashCommandDataBuilder = SlashCommandDataBuilder(Commands.slash(name, description))

        fun message(name: String): CommandDataBuilder = CommandDataBuilder(Commands.message(name))

        fun context(name: String): CommandDataBuilder = CommandDataBuilder(Commands.user(name))
    }
}