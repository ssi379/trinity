package com.nhaarman.ellietest.persistence;

import com.nhaarman.ellietest.core.RepositoryFactory;
import com.nhaarman.ellietest.persistence.MemoryRepositoryFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class MemoryPersistenceModule {

    @Provides
    RepositoryFactory provideRepositoryFactory(final MemoryRepositoryFactory factory) {
        return factory;
    }

}
