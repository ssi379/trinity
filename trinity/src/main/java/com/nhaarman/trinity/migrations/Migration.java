package com.nhaarman.trinity.migrations;

import android.database.sqlite.SQLiteDatabase;

public interface Migration extends Comparable<Migration> {

  int getVersion();

  long getOrder();

  void beforeUp(final SQLiteDatabase database);

  void onUpgrade(final SQLiteDatabase database);

  void afterUp(final SQLiteDatabase database);

  void beforeDown(final SQLiteDatabase database);

  void onDowngrade(final SQLiteDatabase database);

  void afterDown(final SQLiteDatabase database);
}
