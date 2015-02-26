package com.nhaarman.trinity.query.delete;

import org.jetbrains.annotations.NotNull;

public final class From extends ExecutableDeleteQuerySqlPart {

  @NotNull
  private final String mTableName;

  From(@NotNull final Delete delete, @NotNull final String tableName) {
    super(delete);
    mTableName = tableName;
  }

  public Where where(@NotNull final String where, @NotNull final Object... args) {
    return new Where(this, where, args);
  }

  @Override
  @NotNull
  public String getPartSql() {
    return "FROM " + mTableName;
  }
}