package com.nhaarman.trinity.query.create;

import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Unique extends ColumnInfoSqlPart {

  Unique(@Nullable final SqlPart previousSqlPart) {
    super(previousSqlPart);
  }

  public UniqueOnConflict onConflict(@NotNull final ConflictAction action) {
    return new AllOnConflict(this, action);
  }

  @NotNull
  @Override
  protected String getPartSql() {
    return "UNIQUE";
  }
}
