package com.nhaarman.ellietest;

import com.nhaarman.ellietest.persistence.MemoryPersistenceComponent;

import dagger.Component;

@Component(dependencies = MemoryPersistenceComponent.class)
public interface ApplicationComponent {

    void inject(MainActivity application);

}
