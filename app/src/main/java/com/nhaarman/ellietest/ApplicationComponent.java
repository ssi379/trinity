package com.nhaarman.ellietest;

import com.nhaarman.ellietest.persistence.MemoryRepositoryFactoryComponent;

import dagger.Component;

@Component(dependencies = MemoryRepositoryFactoryComponent.class)
public interface ApplicationComponent {

}
