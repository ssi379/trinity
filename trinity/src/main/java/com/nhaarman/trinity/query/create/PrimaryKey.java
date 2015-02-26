package com.nhaarman.trinity.query.create;

import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;

public class PrimaryKey extends ColumnInfoSqlPart {

  public PrimaryKey(@NotNull final SqlPart previousSqlPart) {
    super(previousSqlPart);
  }

  public Ascending ascending() {
    return new Ascending(this);
  }

  public Descending descending() {
    return new Descending(this);
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
    return "PRIMARY KEY";
  }
}
