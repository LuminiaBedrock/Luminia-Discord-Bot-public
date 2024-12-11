package com.luminia.discord.bot;

import com.luminia.config.Config;
import com.luminia.discord.api.command.CommandManager;
import com.luminia.discord.api.handler.EventManager;
import com.luminia.discord.api.handler.SimpleHandler;
import com.luminia.discord.bot.command.CommandManagerImpl;
import com.luminia.discord.bot.command.impl.*;
import com.luminia.discord.bot.handler.*;
import com.luminia.discord.bot.handler.impl.*;
import com.luminia.discord.bot.service.translation.TranslationService;
import com.luminia.discord.bot.service.translation.TranslationServiceImpl;
import com.luminia.discord.bot.settings.repository.SettingsRepository;
import com.luminia.discord.bot.settings.repository.SettingsRepositoryImpl;
import com.mefrreex.jooq.database.IDatabase;
import com.mefrreex.jooq.database.SQLiteDatabase;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import lombok.Getter;

import java.io.File;

@Getter
public class DiscordBot {

    @Getter
    private static DiscordBot instance;

    private final String token;
    private final JDA jda;

    private final EventManager eventManager;
    private final CommandManager commandManager;
    private final TranslationService translationService;

    private final SettingsRepository settingsRepository;

    private final long startTime = System.currentTimeMillis();

    public DiscordBot(String token, Config config) {
        DiscordBot.instance = this;

        this.token = token;
        this.jda = JDABuilder.createDefault(token)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .enableIntents(GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                .setBulkDeleteSplittingEnabled(false)
                .setActivity(Activity.listening("luminia.fun"))
                .build();

        IDatabase database = new SQLiteDatabase(new File("database.db"));
        this.settingsRepository = new SettingsRepositoryImpl(database);

        this.translationService = new TranslationServiceImpl(config);

        this.jda.addEventListener(new EventHandler());
        this.eventManager = new EventManagerImpl(jda);
        this.eventManager.register(
                new SimpleHandler(),
                new CommandInteractionHandler(),
                new ModalInteractionHandler(),
                new ButtonInteractionHandler(),
                new MemberTimeoutHandler(),
                new GuildMemberJoinHandler()
        );

        this.commandManager = new CommandManagerImpl(jda);
        this.commandManager.register(
                new AvatarCommand(),
                new ProfileCommand(),
                new MemberCountCommand(),
                new MembersCommand(),
                new MessageEmbedCommand(),
                new MessageCommand(),
                new MinecraftStatusCommand(),
                new MinecraftServerCommand(),
                new SiteCommand(),
                new RconCommand(),
                new TimeoutCommand(),
                new SetNameCommand()
        );
    }

    public void shutdown() {
        jda.shutdown();
    }
}