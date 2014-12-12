package com.nhaarman.ellietest.persistence;

import com.nhaarman.ellietest.core.RepositoryFactory;

public class MemoryRepositoryFactory implements RepositoryFactory {

    private final MemoryClubRepository mMemoryClubRepository;
    private final MemoryTeamRepository mTeamRepository;
    private final MemoryPlayerRepository mPlayerRepository;

    public MemoryRepositoryFactory() {
        mMemoryClubRepository = new MemoryClubRepository();
        mTeamRepository = new MemoryTeamRepository(mMemoryClubRepository);
        mPlayerRepository = new MemoryPlayerRepository(mTeamRepository);
    }

    @Override
    public MemoryClubRepository createClubRepository() {
        return mMemoryClubRepository;
    }

    @Override
    public MemoryTeamRepository createTeamRepository() {
        return mTeamRepository;
    }

    @Override
    public MemoryPlayerRepository createPlayerRepository() {
        return mPlayerRepository;
    }
}
