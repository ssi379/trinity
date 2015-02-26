package com.nhaarman.trinity.query.create;

import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ColumnInfoSqlPart extends SqlPart implements ColumnInfo {

  ColumnInfoSqlPart(@Nullable final SqlPart previousSqlPart) {
    super(previousSqlPart);
  }

  @Override
  public PrimaryKey primaryKey() {
    return new PrimaryKey(this);
  }

  @Override
  public NotNullConstraint notNull() {
    return new NotNullConstraint(this);
  }

  @Override
  public Unique unique() {
    return new Unique(this);
  }

  @Override
  public Default defaultValue(@NotNull final Object defaultValue) {
    return new Default(this, defaultValue);
  }

  @Override
  public ForeignKey references(@NotNull final String foreignTableName) {
    return new ForeignKey(this, foreignTableName);
  }
}
