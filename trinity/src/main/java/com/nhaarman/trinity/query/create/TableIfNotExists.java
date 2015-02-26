package com.nhaarman.trinity.query.create;

import org.jetbrains.annotations.NotNull;

public class TableIfNotExists extends Table {

  @NotNull
  private final String mTableName;

  protected TableIfNotExists(@NotNull final Create create, @NotNull final String tableName) {
    super(create, tableName);
    mTableName = tableName;
  }

  @NotNull
  @Override
  protected String getPartSql() {
    return "TABLE IF NOT EXISTS " + mTableName;
  }
}