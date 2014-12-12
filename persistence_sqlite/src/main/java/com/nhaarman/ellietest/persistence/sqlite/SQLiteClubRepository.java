package com.nhaarman.ellietest.persistence.sqlite;

import com.nhaarman.ellietest.core.clubs.Club;
import com.nhaarman.ellietest.core.clubs.ClubRepository;

import javax.inject.Inject;

public class SQLiteClubRepository implements ClubRepository {

    @Inject
    SQLiteClubRepository() {

    }

    @Override
    public Long create(final Club object) {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO: Implement create.
    }

    @Override
    public void update(final Club object) {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO: Implement update.
    }

    @Override
    public Club find(final Long id) {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO: Implement find.
    }
}
