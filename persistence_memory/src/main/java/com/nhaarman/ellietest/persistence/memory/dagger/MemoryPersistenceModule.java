package com.nhaarman.ellietest.persistence.memory.dagger;

import com.nhaarman.ellietest.core.clubs.ClubRepository;
import com.nhaarman.ellietest.core.players.PlayerRepository;
import com.nhaarman.ellietest.core.teams.TeamRepository;
import com.nhaarman.ellietest.persistence.memory.MemoryClubRepository;
import com.nhaarman.ellietest.persistence.memory.MemoryPlayerRepository;
import com.nhaarman.ellietest.persistence.memory.MemoryTeamRepository;

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
