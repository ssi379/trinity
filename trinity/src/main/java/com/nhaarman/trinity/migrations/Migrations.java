/*
 * Copyright 2015 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nhaarman.trinity.migrations;

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
