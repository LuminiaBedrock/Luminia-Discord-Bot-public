package com.luminia.discord.bot.command.impl;

import com.luminia.discord.api.command.SlashCommand
import com.luminia.discord.api.command.data.CommandData
import com.luminia.discord.bot.service.translation.TranslationFormat
import com.luminia.discord.bot.service.translation.TranslationService
import com.luminia.discord.bot.utils.BotColors
import com.luminia.discord.bot.utils.BotUtils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData

class ProfileCommand : SlashCommand() {

    private val translationService = TranslationService.getInstance()

    override fun getCommandData(): CommandData {
        return CommandData.slash("profile", TranslationFormat.commandDescription("profile"))
            .addOptions(OptionData(OptionType.USER, "user", translationService.translate("command-profile-option-user-name")))
    }

    override fun execute(event: SlashCommandInteractionEvent) {
        event.getOption("user")?.asMember?.let { member ->
            val builder = StringBuilder()
                .append(translationService.translate("command-profile-embed-user-name", member.effectiveName) + "\n")
                .append(translationService.translate("command-profile-embed-user-id", member.user.name) + "\n")
                .append(translationService.translate("command-profile-embed-user-id-long", member.idLong) + "\n")
                .append(translationService.translate("command-profile-embed-user-online-status", member.onlineStatus.name, BotUtils.getEmojiFromStatus(member.onlineStatus).formatted))

            val embed = EmbedBuilder()
                .setTitle(translationService.translate("command-profile-embed-title"))
                .setDescription(builder.toString())
                .setThumbnail(member.user.avatarUrl)
                .setColor(BotColors.PRIMARY)
                .build()

//            TODO buttons
//            val canInteract = member.canInteract(event.guild!!.selfMember)
//            val canPunish = !canInteract && member != event.guild!!.selfMember
//
//            val buttons = listOf(
//                ActionRow.of(
//                    Button.danger("ban", "Забанить").withDisabled(!canPunish),
//                    Button.danger("kick", "Кикнуть").withDisabled(!canPunish)
//                ),
//                ActionRow.of(
//                    Button.primary("change_name", "Изменить имя")
//                        .withDisabled(canInteract)
//                        .setHandler { changeName(member, it) }
//                )
//            )

            event.replyEmbeds(embed)
                .queue()
        }
    }

//    private fun changeName(member: Member, event: ButtonInteractionEvent) {
//        if (!BotUtils.canInteractWithPermission(event.member, member, Permission.NICKNAME_CHANGE)) {
//            event.reply("Вы не можете изменять имя").setEphemeral(true).queue()
//            return
//        }
//
//        val nameInput = TextInput.create("name", "Имя", TextInputStyle.SHORT)
//            .setPlaceholder(member.effectiveName)
//            .setMinLength(1)
//            .setMaxLength(32)
//            .build()
//
//        val modal = Modal.create("change_name", "Изменить имя")
//            .addActionRow(nameInput)
//            .build()
//
//        modal.setHandler { interaction ->
//            try {
//                val name = interaction.getValue("name")?.asString
//                member.modifyNickname(name).queue {
//                    event.reply("Имя изменено на $name").setEphemeral(true).queue()
//                }
//            } catch (e: HierarchyException) {
//                event.reply("Бот не может изменить имя этого пользователя").setEphemeral(true).queue()
//            }
//        }
//
//        event.replyModal(modal).queue()
//    }
}
