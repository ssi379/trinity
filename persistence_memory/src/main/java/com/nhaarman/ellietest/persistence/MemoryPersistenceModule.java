package com.nhaarman.ellietest.persistence;

import com.nhaarman.ellietest.core.clubs.ClubRepository;
import com.nhaarman.ellietest.core.players.PlayerRepository;
import com.nhaarman.ellietest.core.teams.TeamRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MemoryPersistenceModule {

    @Provides
    @Singleton
    ClubRepository provideClubRepository(final MemoryClubRepository clubRepository) {
        return clubRepository;
    }

    @Provides
    @Singleton
    TeamRepository provideTeamRepository(final MemoryTeamRepository teamRepository) {
        return teamRepository;
    }

    @Provides
    @Singleton
    PlayerRepository providePlayerRepository(final MemoryPlayerRepository teamRepository) {
        return teamRepository;
    }

}
