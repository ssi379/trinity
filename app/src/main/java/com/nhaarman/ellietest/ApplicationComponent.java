package com.nhaarman.ellietest;

import com.nhaarman.ellietest.persistence.dagger.MemoryPersistenceComponent;

import dagger.Component;

@Component(dependencies = MemoryPersistenceComponent.class)
public interface ApplicationComponent {

    void inject(MainActivity application);

}
