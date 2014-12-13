package com.nhaarman.ellietest.persistence.memory;

import com.nhaarman.ellietest.core.clubs.Club;
import com.nhaarman.ellietest.core.clubs.ClubRepository;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class MemoryClubRepository implements ClubRepository {

    private final Map<Long, Club> mTable;
    private Long mLastId = 0L;

    @Inject
    public MemoryClubRepository() {
        mTable = new HashMap<>();
    }

    @Override
    public Long create(final Club object) {
        mLastId++;
        mTable.put(mLastId, object);
        object.setId(mLastId);
        return mLastId;
    }

    @Override
    public void update(final Club object) {
        mTable.put(object.getId(), object);
    }

    @Override
    public Club find(final Long id) {
        return mTable.get(id);
    }
}