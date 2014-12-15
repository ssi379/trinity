package com.nhaarman.sql_lib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nhaarman.lib_setup.migrations.Migration;
import com.nhaarman.sql_lib.migrations.Migrations;

import java.util.List;
import java.util.ListIterator;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 2;

    private final Migrations mMigrations;

    @Inject
    public SQLiteDatabaseHelper(final Context context, final Migrations migrations) {
        super(context, "name", null, VERSION);
        mMigrations = migrations;
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        System.out.println("Executing create statements");
        executeUpMigrations(db, 1);

        if (VERSION > 1) {
            onUpgrade(db, 1, VERSION);
        }
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        System.out.println("Upgrading database from version " + oldVersion + " to " + newVersion);
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
        migration.beforeUp();

        for (String statement : migration.getUpStatements()) {
            System.out.println(statement);
            database.execSQL(statement);
        }

        migration.afterUp();
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
        migration.beforeDown();

        for (String statement : migration.getDownStatements()) {
            System.out.println(statement);
            database.execSQL(statement);
        }

        migration.afterDown();
    }
}
