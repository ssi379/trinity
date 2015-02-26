package com.nhaarman.trinity.query;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.jetbrains.annotations.NotNull;

public abstract class ExecutableQuerySqlStart extends SqlStart implements ExecutableQuery {

  @NotNull
  @Override
  public String getSql() {
    return getPartSql();
  }

  @NotNull
  @Override
  public Cursor queryOn(@NotNull final SQLiteDatabase database) {
    return database.rawQuery(getSql(), getArguments());
  }
}
