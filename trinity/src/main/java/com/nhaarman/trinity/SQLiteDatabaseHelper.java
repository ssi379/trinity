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

package com.nhaarman.trinity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nhaarman.trinity.migrations.Migration;
import com.nhaarman.trinity.migrations.Migrations;
import java.util.SortedSet;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

  private final Migrations mMigrations;

  public SQLiteDatabaseHelper(final Context context, final String databaseName, final Migrations migrations) {
    super(context, databaseName, null, findVersionNumber(migrations));
    mMigrations = migrations;
  }

  @Override
  public void onCreate(final SQLiteDatabase db) {
    executeUpMigrations(db, 1);

    int versionNumber = findVersionNumber(mMigrations);
    if (versionNumber > 1) {
      onUpgrade(db, 1, versionNumber);
    }
  }

  @Override
  public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
    for (int version = oldVersion + 1; version <= newVersion; version++) {
      executeUpMigrations(db, version);
    }
  }

  @Override
  public void onDowngrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
    for (int version = oldVersion; version > newVersion; version--) {
      executeDownMigrations(db, version);
    }
  }

  private void executeUpMigrations(final SQLiteDatabase database, final int version) {
    SortedSet<Migration> migrations = mMigrations.getMigrationsForVersion(version);
    for (Migration migration : migrations) {
      executeUpMigration(database, migration);
    }
  }

  private void executeUpMigration(final SQLiteDatabase database, final Migration migration) {
    migration.beforeUp(database);
    migration.onUpgrade(database);
    migration.afterUp(database);
  }

  private void executeDownMigrations(final SQLiteDatabase database, final int version) {
    SortedSet<Migration> migrations = mMigrations.getMigrationsForVersion(version);

    Migration[] migrationsArray = migrations.toArray(new Migration[migrations.size()]);
    for (int i = migrationsArray.length - 1; i >= 0; i--) {
      executeDownMigration(database, migrationsArray[i]);
    }
  }

  private void executeDownMigration(final SQLiteDatabase database, final Migration migration) {
    migration.beforeDown(database);
    migration.onDowngrade(database);
    migration.afterDown(database);
  }

  private static int findVersionNumber(final Migrations migrations) {
    return migrations.getVersionNumber();
  }
}
