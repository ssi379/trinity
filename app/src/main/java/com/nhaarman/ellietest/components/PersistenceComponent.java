package com.nhaarman.ellietest.components;

import com.nhaarman.ellietest.core.clubs.ClubRepository;
import com.nhaarman.ellietest.core.players.PlayerRepository;
import com.nhaarman.ellietest.core.teams.TeamRepository;
import com.nhaarman.ellietest.persistence.memory.dagger.MemoryPersistenceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = MemoryPersistenceModule.class)
public interface PersistenceComponent {

    ClubRepository clubRepository();

    TeamRepository teamRepository();

    PlayerRepository playerRepository();
}
