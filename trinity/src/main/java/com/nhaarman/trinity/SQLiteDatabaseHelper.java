package com.nhaarman.trinity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nhaarman.trinity.migrations.Migration;
import com.nhaarman.trinity.migrations.Migrations;
import java.util.List;
import java.util.ListIterator;

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
    List<Migration> migrations = mMigrations.getMigrationsForVersion(version);
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
    List<Migration> migrations = mMigrations.getMigrationsForVersion(version);

    ListIterator<Migration> iterator = migrations.listIterator(migrations.size());
    while (iterator.hasPrevious()) {
      Migration migration = iterator.previous();
      executeDownMigration(database, migration);
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
