package com.nhaarman.ellietest.persistence;

import com.nhaarman.ellietest.core.RepositoryFactory;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = MemoryPersistenceModule.class
)
public interface MemoryRepositoryFactoryComponent {

    RepositoryFactory factory();
}