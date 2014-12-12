package com.nhaarman.ellietest.persistence.sqlite;

import com.nhaarman.ellietest.core.players.Player;
import com.nhaarman.ellietest.core.players.PlayerRepository;

import javax.inject.Inject;

public class SQLitePlayerRepository implements PlayerRepository {

    @Inject
    SQLitePlayerRepository() {

    }

    @Override
    public Long create(final Player object) {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO: Implement create.
    }

    @Override
    public void update(final Player object) {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO: Implement update.
    }

    @Override
    public Player find(final Long id) {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO: Implement find.
    }
}
