package com.nhaarman.ellietest.components;

import com.nhaarman.ellietest.MainActivity;

import dagger.Component;

@Component(dependencies = PersistenceComponent.class)
public interface ApplicationComponent {

    void inject(MainActivity application);

}
