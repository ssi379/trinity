package com.nhaarman.lib_sql_generated.dagger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.nhaarman.ellietest.core.clubs.ClubRepository;
import com.nhaarman.ellietest.core.players.PlayerRepository;
import com.nhaarman.ellietest.core.teams.TeamRepository;
import com.nhaarman.ellietest.persistence.sqlite.SQLiteClubRepository;
import com.nhaarman.lib_sql_generated.SQLitePlayerRepository;
import com.nhaarman.lib_sql_generated.SQLiteTeamRepository;
import com.nhaarman.sql_lib.SQLiteDatabaseHelper;
import com.nhaarman.lib_sql_generated.migrations.MigrationFactory;
import com.nhaarman.sql_lib.migrations.Migrations;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SQLitePersistenceModule {

    private final Context mContext;

    public SQLitePersistenceModule(final Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Provides
    @Singleton
    ClubRepository provideClubRepository(final SQLiteClubRepository clubRepository) {
        return clubRepository;
    }

    @Provides
    @Singleton
    TeamRepository provideTeamRepository(final SQLiteTeamRepository teamRepository) {
        return teamRepository;
    }

    @Provides
    @Singleton
    PlayerRepository providePlayerRepository(final SQLitePlayerRepository teamRepository) {
        return teamRepository;
    }

    @Provides
    @Singleton
    SQLiteDatabase provideDatabase(final SQLiteDatabaseHelper databaseHelper) {
        return databaseHelper.getWritableDatabase();
    }

    @Provides
    @Singleton
    Migrations provideMigrations(final MigrationFactory migrationFactory) {
        return migrationFactory.createMigrations();
    }

}
