package com.nhaarman.trinity.migrations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Migrations {

  /**
   * The migrations to run on the database.
   * Keys are database versions.
   */
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
