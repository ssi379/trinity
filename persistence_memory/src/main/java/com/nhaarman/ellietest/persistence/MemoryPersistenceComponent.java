package com.nhaarman.ellietest.persistence;

import com.nhaarman.ellietest.core.clubs.ClubRepository;
import com.nhaarman.ellietest.core.players.PlayerRepository;
import com.nhaarman.ellietest.core.teams.TeamRepository;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = MemoryPersistenceModule.class) @Singleton
public interface MemoryPersistenceComponent {

    ClubRepository clubRepository();

    TeamRepository teamRepository();

    PlayerRepository playerRepository();
}
