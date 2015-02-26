package com.nhaarman.trinity.query.create;

import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Ascending extends ColumnInfoSqlPart {

  Ascending(@Nullable final SqlPart sqlPart) {
    super(sqlPart);
  }

  public AutoIncrement autoIncrement() {
    return new AutoIncrement(this);
  }

  public PrimaryKeyOnConflict onConflict(@NotNull final ConflictAction action) {
    return new AllOnConflict(this, action);
  }

  @NotNull
  @Override
  protected String getPartSql() {
    return "ASC";
  }
}