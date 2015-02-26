package com.nhaarman.trinity.query.create;

import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ForeignKey extends ColumnInfoSqlPart {

  @NotNull
  private final String mForeignTableName;

  ForeignKey(@Nullable final SqlPart previousSqlPart, @NotNull final String foreignTableName) {
    super(previousSqlPart);
    mForeignTableName = foreignTableName;
  }

  public ColumnNames columns(@NotNull final String... columnNames) {
    return new ColumnNames(this, columnNames);
  }

  public ColumnInfo onDelete(@NotNull final OnAction onAction) {
    return new OnDelete(this, onAction);
  }

  public ColumnInfo onUpdate(@NotNull final OnAction onAction) {
    return new OnUpdate(this, onAction);
  }

  @NotNull
  @Override
  protected String getPartSql() {
    return "REFERENCES " + mForeignTableName;
  }
}
