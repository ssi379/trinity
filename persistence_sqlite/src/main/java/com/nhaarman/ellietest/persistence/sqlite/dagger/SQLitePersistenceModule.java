package com.nhaarman.ellietest.persistence.sqlite.dagger;

import com.nhaarman.ellietest.core.clubs.ClubRepository;
import com.nhaarman.ellietest.core.players.PlayerRepository;
import com.nhaarman.ellietest.core.teams.TeamRepository;
import com.nhaarman.ellietest.persistence.sqlite.SQLiteClubRepository;
import com.nhaarman.ellietest.persistence.sqlite.SQLitePlayerRepository;
import com.nhaarman.ellietest.persistence.sqlite.SQLiteTeamRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SQLitePersistenceModule {

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

}
