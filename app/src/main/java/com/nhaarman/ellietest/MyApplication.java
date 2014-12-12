package com.nhaarman.ellietest;


import com.nhaarman.ellietest.persistence.Dagger_MemoryPersistenceComponent;

public class MyApplication extends android.app.Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        mApplicationComponent = Dagger_ApplicationComponent.builder().memoryPersistenceComponent(Dagger_MemoryPersistenceComponent.create()).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}


