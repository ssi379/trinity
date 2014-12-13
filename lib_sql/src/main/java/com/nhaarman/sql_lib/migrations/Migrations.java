package com.nhaarman.sql_lib.migrations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Migrations {

    private final Map<Integer, List<Migration>> mMigrations;

    public Migrations() {
        mMigrations = new HashMap<>();
    }

    public List<Migration> getMigrationsForVersion(final int version) {
        List<Migration> migrations = getMigrations(version);
        Collections.sort(migrations);
        return Collections.unmodifiableList(migrations);
    }

    public void addMigration(final Migration migration) {
        List<Migration> migrations = getMigrations(migration.getVersion());
        migrations.add(migration);
        mMigrations.put(migration.getVersion(), migrations);
    }

    private List<Migration> getMigrations(final int version) {
        return mMigrations.get(version) == null ? new ArrayList<Migration>() : mMigrations.get(version);
    }

}
