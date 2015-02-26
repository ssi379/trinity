package com.nhaarman.trinity.query.create;

import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;

class AllOnConflict extends ColumnInfoSqlPart implements PrimaryKeyOnConflict, NotNullOnConflict, UniqueOnConflict {

  @NotNull
  private final ConflictAction mAction;

  AllOnConflict(@NotNull final SqlPart sqlPart, @NotNull final ConflictAction action) {
    super(sqlPart);
    mAction = action;
  }

  @Override
  public AutoIncrement autoIncrement() {
    return new AutoIncrement(this);
  }

  @Override
  public Default defaultValue(@NotNull final Object defaultValue) {
    return new Default(this, defaultValue);
  }

  @Override
  public ForeignKey references(@NotNull final String foreignTableName) {
    return new ForeignKey(this, foreignTableName);
  }

  public PrimaryKeyOnConflict onConflict(@NotNull final ConflictAction action) {
    return new AllOnConflict(this, action);
  }

  @NotNull
  @Override
  protected String getPartSql() {
    return "ON CONFLICT " + mAction;
  }
}
