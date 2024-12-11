package com.luminia.discord.bot.utils;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.utils.FileUpload;

public class BotUtils {

    public static Emoji getEmojiFromStatus(OnlineStatus status) {
        return Emoji.fromUnicode(
                switch (status) {
                    case ONLINE -> ":green_circle:";
                    case IDLE -> ":yellow_circle:";
                    case DO_NOT_DISTURB -> ":red_circle:";
                    default -> ":black_circle:";
                }
        );
    }

    public static FileUpload getFileUploadFromAttachment(Message.Attachment attachment) {
        return FileUpload.fromData(attachment.getProxy().download().join(), attachment.getFileName());
    }

    public static boolean canInteract(Member member, Member interactable) {
        return canInteractWithPermission(member, interactable, null);
    }

    public static boolean canInteractWithPermission(Member member, Member interactable, Permission permission) {
        return interactable.canInteract(member) && (permission == null || member.hasPermission(permission));
    }
}
