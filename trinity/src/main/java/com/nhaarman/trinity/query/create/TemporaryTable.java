package com.nhaarman.trinity.query.create;

import org.jetbrains.annotations.NotNull;

public class TemporaryTable extends Table {

  @NotNull
  private final String mTableName;

  TemporaryTable(@NotNull final Create create, @NotNull final String tableName) {
    super(create, tableName);
    mTableName = tableName;
  }

  @NotNull
  @Override
  protected String getPartSql() {
    return "TEMPORARY TABLE " + mTableName;
  }
}