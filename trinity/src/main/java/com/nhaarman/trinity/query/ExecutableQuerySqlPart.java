package com.nhaarman.trinity.query;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ExecutableQuerySqlPart extends SqlPart implements ExecutableQuery {

  protected ExecutableQuerySqlPart(@Nullable final SqlPart previousSqlPart) {
    super(previousSqlPart);
  }

  @NotNull
  @Override
  public Cursor queryOn(@NotNull final SQLiteDatabase database) {
    return database.rawQuery(getSql(), getArguments());
  }
}
