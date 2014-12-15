package com.nhaarman.ellietest;


import android.app.Application;

import com.nhaarman.ellietest.components.ApplicationComponent;
import com.nhaarman.ellietest.components.Dagger_ApplicationComponent;
import com.nhaarman.ellietest.components.PersistenceComponent;

public class MyApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        PersistenceComponent persistenceComponent = new PersistenceComponent.Builder().create(this);
        mApplicationComponent = Dagger_ApplicationComponent.builder().persistenceComponent(persistenceComponent).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}


