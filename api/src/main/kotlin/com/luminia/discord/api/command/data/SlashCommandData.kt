package com.luminia.discord.api.command.data

import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData

interface SlashCommandData : CommandData {

    fun setDescription(description: String): SlashCommandData

    fun addOptions(vararg options: OptionData): SlashCommandData

    fun addOptions(options: Collection<OptionData>): SlashCommandData

    fun addOption(type: OptionType, name: String, description: String, required: Boolean, autoComplete: Boolean): SlashCommandData

    fun addOption(type: OptionType, name: String, description: String, required: Boolean): SlashCommandData

    fun addOption(type: OptionType, name: String, description: String): SlashCommandData

    fun addSubcommands(vararg subcommands: SubcommandData): SlashCommandData

    fun addSubcommands(subcommands: Collection<SubcommandData>): SlashCommandData

    fun addSubcommandGroups(vararg groups: SubcommandGroupData): SlashCommandData

    fun addSubcommandGroups(groups: Collection<SubcommandGroupData>): SlashCommandData
}