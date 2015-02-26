package com.nhaarman.trinity.query;

import android.database.sqlite.SQLiteDatabase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.nhaarman.trinity.query.QueryExecutor.execute;

public abstract class ExecutableStatementSqlPart extends SqlPart implements ExecutableStatement {

  protected ExecutableStatementSqlPart(@Nullable final SqlPart previousSqlPart) {
    super(previousSqlPart);
  }

  @Override
  public void executeOn(@NotNull final SQLiteDatabase database) {
    execute(this, database);
  }
}
