package com.nhaarman.ellietest;

import com.nhaarman.ellietest.core.RepositoryFactory;
//import com.nhaarman.ellietest.persistence.Dagger_MemoryRepositoryFactoryComponent;
import com.nhaarman.ellietest.persistence.Dagger_MemoryRepositoryFactoryComponent;
import com.nhaarman.ellietest.persistence.MemoryRepositoryFactoryComponent;

public class MyApplication extends android.app.Application {

    private MemoryRepositoryFactoryComponent mMemoryRepositoryFactoryComponent;
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        mMemoryRepositoryFactoryComponent = Dagger_MemoryRepositoryFactoryComponent.create();
//        mApplicationComponent = Dagger_ApplicationComponent.builder().memoryRepositoryFactoryComponent(mMemoryRepositoryFactoryComponent).build();
    }

    public RepositoryFactory getRepositoryFactory() {
        return mMemoryRepositoryFactoryComponent.factory();
    }

}


