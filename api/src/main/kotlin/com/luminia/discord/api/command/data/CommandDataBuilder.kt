package com.luminia.discord.api.command.data

import com.luminia.discord.api.command.permission.CommandPermission
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.build.CommandData as JDACommandData

open class CommandDataBuilder(private val jdaCommandData: JDACommandData) : CommandData {

    private var permission: CommandPermission = CommandPermission.DEFAULT

    override fun getJDACommandData(): JDACommandData = jdaCommandData

    override fun getPermission(): CommandPermission = permission

    override fun setName(name: String): CommandDataBuilder {
        jdaCommandData.setName(name)
        return this
    }

    override fun setDefaultPermissions(permission: DefaultMemberPermissions): CommandDataBuilder {
        jdaCommandData.setDefaultPermissions(permission)
        return this
    }

    override fun setPermission(permission: CommandPermission): CommandDataBuilder {
        this.permission = permission
        return this
    }

    override fun setGuildOnly(guildOnly: Boolean): CommandDataBuilder {
        jdaCommandData.setGuildOnly(guildOnly)
        return this
    }

    override fun setNSFW(nsfw: Boolean): CommandDataBuilder {
        jdaCommandData.setNSFW(nsfw)
        return this
    }
}