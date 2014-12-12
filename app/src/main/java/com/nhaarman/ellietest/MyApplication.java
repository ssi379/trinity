package com.nhaarman.ellietest;

import com.nhaarman.ellietest.core.RepositoryFactory;
import com.nhaarman.ellietest.persistence.MemoryRepositoryFactory;

public class MyApplication extends android.app.Application {

    private final MemoryRepositoryFactory mMemoryRepositoryFactory;

    public MyApplication() {
        mMemoryRepositoryFactory = new MemoryRepositoryFactory();
    }

    public RepositoryFactory getRepositoryFactory() {
        return mMemoryRepositoryFactory;
    }

    @Override
    public void onCreate() {

    }
}


