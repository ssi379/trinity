package com.nhaarman.trinity.migrations;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Migrations {

  /**
   * The migrations to run on the database.
   * Keys are database versions.
   */
  private final Map<Integer, SortedSet<Migration>> mMigrations;

  public Migrations() {
    mMigrations = new HashMap<>();
  }

  public SortedSet<Migration> getMigrationsForVersion(final int version) {
    SortedSet<Migration> migrations = getMigrations(version);
    return Collections.unmodifiableSortedSet(migrations);
  }

  public void addMigration(final Migration migration) {
    SortedSet<Migration> migrations = getMigrations(migration.getVersion());
    migrations.add(migration);
    mMigrations.put(migration.getVersion(), migrations);
  }

  private SortedSet<Migration> getMigrations(final int version) {
    return mMigrations.get(version) == null ? new TreeSet<Migration>() : mMigrations.get(version);
  }

  /**
   * Returns the highest version of the added Migrations.
   */
  public int getVersionNumber() {
    Set<Integer> integers = mMigrations.keySet();
    int max = Integer.MIN_VALUE;
    for (Integer version : integers) {
      if (version > max) {
        max = version;
      }
    }

    return max;
  }
}
