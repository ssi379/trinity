package com.nhaarman.ellietest.persistence;

import com.nhaarman.ellietest.core.players.Player;
import com.nhaarman.ellietest.core.players.PlayerRepository;
import com.nhaarman.ellietest.core.teams.TeamRepository;

import java.util.HashMap;
import java.util.Map;

public class MemoryPlayerRepository implements PlayerRepository {

    private final Map<Long, Player> mTable;
    private final TeamRepository mTeamRepository;
    private Long mLastId = 0L;

    public MemoryPlayerRepository(final TeamRepository teamRepository) {
        mTeamRepository = teamRepository;
        mTable = new HashMap<>();
    }

    @Override
    public Long create(final Player object) {
        mLastId++;
        mTable.put(mLastId, object);
        object.setId(mLastId);
        return mLastId;
    }

    @Override
    public void update(final Player object) {
        mTable.put(object.getId(), object);
        mTeamRepository.update(object.getTeam());
    }

    @Override
    public Player find(final Long id) {
        return mTable.get(id);
    }
}
