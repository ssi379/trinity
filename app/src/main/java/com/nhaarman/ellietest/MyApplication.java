package com.nhaarman.ellietest;

import com.nhaarman.ellietest.components.ApplicationComponent;
import com.nhaarman.ellietest.components.PersistenceComponent;

public class MyApplication extends android.app.Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        PersistenceComponent persistenceComponent = Dagger_PersistenceComponent.create();
        mApplicationComponent = Dagger_ApplicationComponent.builder().persistenceComponent(persistenceComponent).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}


