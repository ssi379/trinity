package com.nhaarman.ellietest.persistence.sqlite;


import com.nhaarman.ellietest.core.teams.Team;
import com.nhaarman.ellietest.core.teams.TeamRepository;

import javax.inject.Inject;

public class SQLiteTeamRepository implements TeamRepository {

    @Inject
    SQLiteTeamRepository() {
    }

    @Override
    public Long create(final Team object) {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO: Implement create.
    }

    @Override
    public void update(final Team object) {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO: Implement update.
    }

    @Override
    public Team find(final Long id) {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO: Implement find.
    }
}
