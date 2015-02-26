package com.nhaarman.trinity.query.create;

import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NotNullConstraint extends ColumnInfoSqlPart {

  NotNullConstraint(@Nullable final SqlPart previousSqlPart) {
    super(previousSqlPart);
  }

  public NotNullOnConflict onConflict(@NotNull final ConflictAction action) {
    return new AllOnConflict(this, action);
  }

  @NotNull
  @Override
  protected String getPartSql() {
    return "NOT NULL";
  }
}
