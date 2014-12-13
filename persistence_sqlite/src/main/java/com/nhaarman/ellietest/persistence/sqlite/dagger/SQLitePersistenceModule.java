package com.nhaarman.ellietest.persistence.sqlite.dagger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.nhaarman.ellietest.core.clubs.ClubRepository;
import com.nhaarman.ellietest.core.players.PlayerRepository;
import com.nhaarman.ellietest.core.teams.TeamRepository;
import com.nhaarman.ellietest.persistence.sqlite.SQLiteClubRepository;
import com.nhaarman.ellietest.persistence.sqlite.SQLiteDatabaseHelper;
import com.nhaarman.ellietest.persistence.sqlite.SQLitePlayerRepository;
import com.nhaarman.ellietest.persistence.sqlite.SQLiteTeamRepository;
import com.nhaarman.ellietest.persistence.sqlite.migrations.MigrationFactory;
import com.nhaarman.ellietest.persistence.sqlite.migrations.Migrations;

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
