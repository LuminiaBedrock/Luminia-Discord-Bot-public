package com.luminia.discord.api.command.permission

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Role

interface CommandPermission {

    fun defaultValue(): Boolean {
        return false
    }

    fun test(member: Member): Boolean

    companion object {

        fun role(roleId: Long): CommandPermission {
            return object : CommandPermission {
                override fun test(member: Member): Boolean {
                    return member.roles.stream().anyMatch { role: Role -> role.idLong == roleId }
                }
            }
        }

        fun permission(permission: Permission?): CommandPermission {
            return object : CommandPermission {
                override fun test(member: Member): Boolean {
                    return member.hasPermission(permission)
                }
            }
        }

        val DEFAULT: CommandPermission = object : CommandPermission {

            override fun defaultValue(): Boolean {
                return true;
            }

            override fun test(member: Member): Boolean {
                return true
            }
        }
    }
}