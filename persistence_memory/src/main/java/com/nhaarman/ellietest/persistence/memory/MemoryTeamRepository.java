package com.nhaarman.ellietest.persistence.memory;

import com.nhaarman.ellietest.core.clubs.ClubRepository;
import com.nhaarman.ellietest.core.teams.Team;
import com.nhaarman.ellietest.core.teams.TeamRepository;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class MemoryTeamRepository implements TeamRepository {

    private final Map<Long, Team> mTable;
    private final ClubRepository mClubRepository;
    private Long mLastId = 0L;

    @Inject
    public MemoryTeamRepository(final ClubRepository clubRepository) {
        mClubRepository = clubRepository;
        mTable = new HashMap<>();
    }

    @Override
    public Long create(final Team object) {
        mLastId++;
        mTable.put(mLastId, object);
        object.setId(mLastId);
        return mLastId;
    }

    @Override
    public boolean update(final Team object) {
        mTable.put(object.getId(), object);
        mClubRepository.update(object.getClub());
    }

    @Override
    public Team find(final Long id) {
        return mTable.get(id);
    }
}
