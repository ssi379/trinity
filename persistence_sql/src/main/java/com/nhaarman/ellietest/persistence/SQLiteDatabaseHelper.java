package com.nhaarman.ellietest.persistence;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    public SQLiteDatabaseHelper(final Context context, final String name, final SQLiteDatabase.CursorFactory factory, final int version) {
        super(context, name, factory, version);
    }

    public SQLiteDatabaseHelper(final Context context, final String name, final SQLiteDatabase.CursorFactory factory, final int version, final DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO: Implement onCreate.
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO: Implement onUpgrade.
    }
}
