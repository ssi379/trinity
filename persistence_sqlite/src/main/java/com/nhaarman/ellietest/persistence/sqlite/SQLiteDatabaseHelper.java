package com.nhaarman.ellietest.persistence.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    @Inject
    public SQLiteDatabaseHelper(final Context context) {
        super(context, "name", null, 1);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL("CREATE TABLE players (id INT PRIMARY KEY NOT NULL, name STRING NOT NULL)");
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO: Implement onUpgrade.
    }
}
