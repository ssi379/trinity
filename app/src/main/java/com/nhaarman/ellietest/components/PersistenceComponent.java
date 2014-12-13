package com.nhaarman.ellietest.components;

import android.content.Context;

import com.nhaarman.ellietest.core.clubs.ClubRepository;
import com.nhaarman.ellietest.core.players.PlayerRepository;
import com.nhaarman.ellietest.core.teams.TeamRepository;
import com.nhaarman.ellietest.persistence.sqlite.dagger.*;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = SQLitePersistenceModule.class)
public interface PersistenceComponent {

    ClubRepository clubRepository();

    TeamRepository teamRepository();

    PlayerRepository playerRepository();

    @SuppressWarnings("PublicInnerClass") class Builder {

        public PersistenceComponent create(final Context context) {
            return Dagger_PersistenceComponent.builder().sQLitePersistenceModule(new SQLitePersistenceModule(context)).build();
        }
    }
}
