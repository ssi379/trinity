package com.nhaarman.trinity.query;

import android.database.sqlite.SQLiteDatabase;
import org.jetbrains.annotations.NotNull;

public class QueryExecutor {

  private QueryExecutor() {
  }

  public static void execute(@NotNull final Executable query, @NotNull final SQLiteDatabase database) {
    database.execSQL(query.getSql());
  }
}
