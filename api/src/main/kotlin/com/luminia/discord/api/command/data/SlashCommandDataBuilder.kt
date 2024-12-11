package com.luminia.discord.api.command.data

import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData as JDASlashCommandData

class SlashCommandDataBuilder(private val jdaCommandData: JDASlashCommandData) : CommandDataBuilder(jdaCommandData), SlashCommandData {

    override fun setDescription(description: String): SlashCommandDataBuilder {
        jdaCommandData.setDescription(description)
        return this
    }

    override fun addOptions(vararg options: OptionData): SlashCommandDataBuilder {
        jdaCommandData.addOptions(*options)
        return this
    }

    override fun addOptions(options: Collection<OptionData>): SlashCommandDataBuilder {
        jdaCommandData.addOptions(options)
        return this
    }

    override fun addOption(
        type: OptionType,
        name: String,
        description: String,
        required: Boolean,
        autoComplete: Boolean
    ): SlashCommandDataBuilder {
        jdaCommandData.addOption(type, name, description, required, autoComplete)
        return this
    }

    override fun addOption(type: OptionType, name: String, description: String, required: Boolean): SlashCommandDataBuilder {
        jdaCommandData.addOption(type, name, description, required)
        return this
    }

    override fun addOption(type: OptionType, name: String, description: String): SlashCommandDataBuilder {
        jdaCommandData.addOption(type, name, description)
        return this
    }

    override fun addSubcommands(vararg subcommands: SubcommandData): SlashCommandDataBuilder {
        jdaCommandData.addSubcommands(*subcommands)
        return this
    }

    override fun addSubcommands(subcommands: Collection<SubcommandData>): SlashCommandDataBuilder {
        jdaCommandData.addSubcommands(subcommands)
        return this
    }

    override fun addSubcommandGroups(vararg groups: SubcommandGroupData): SlashCommandDataBuilder {
        jdaCommandData.addSubcommandGroups(*groups)
        return this
    }

    override fun addSubcommandGroups(groups: Collection<SubcommandGroupData>): SlashCommandDataBuilder {
        jdaCommandData.addSubcommandGroups(groups)
        return this
    }
}