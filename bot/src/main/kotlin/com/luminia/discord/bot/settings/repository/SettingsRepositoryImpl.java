package com.luminia.discord.bot.settings.repository;

import com.luminia.discord.bot.settings.option.Option;
import com.mefrreex.jooq.database.IDatabase;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.util.concurrent.CompletableFuture;

public class SettingsRepositoryImpl implements SettingsRepository {

    private final IDatabase database;
    private final Table<?> table;

    public SettingsRepositoryImpl(IDatabase database) {
        this.database = database;
        this.table = DSL.table("settings");

        database.getConnection().thenAcceptAsync(connection -> {
            DSL.using(connection)
                    .createTableIfNotExists(table)
                    .column("id", SQLDataType.BIGINT)
                    .column("name", SQLDataType.VARCHAR)
                    .column("value", SQLDataType.VARCHAR)
                    .execute();
        }).join();
    }

    @Override
    public <T> CompletableFuture<Void> setOption(long id, Option<T> option, Object value) {
        return database.getConnection().thenAcceptAsync(connection -> {
            int updatedRows = DSL.using(connection).update(table)
                    .set(DSL.field("value"), value.toString())
                    .where(DSL.field("id").eq(id))
                    .and(DSL.field("name").eq(option.getName()))
                    .execute();
            if (updatedRows == 0) {
                DSL.using(connection).insertInto(table)
                        .set(DSL.field("id"), id)
                        .set(DSL.field("name"), option.getName())
                        .set(DSL.field("value"), value.toString())
                        .execute();
            }
        });
    }

    @Override
    public <T> CompletableFuture<T> getOption(long id, Option<T> option) {
        return this.getOptionOrDefault(id, option, option.getDefaultValue());
    }

    @Override
    public <T> CompletableFuture<T> getOptionOrDefault(long id, Option<T> option, T defaultValue) {
        return database.getConnection().thenApplyAsync(connection -> {
            Result<Record> result = DSL.using(connection)
                    .select()
                    .from(table)
                    .where(DSL.field("id").eq(id))
                    .and(DSL.field("name").eq(option.getName()))
                    .fetch();

            if (result.isNotEmpty()) {
                return option.asType(result.getFirst().get(DSL.field("value", String.class)));
            } else {
                return defaultValue;
            }
        });
    }
}
