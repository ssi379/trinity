package com.nhaarman.trinity.query.create;

import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("HardCodedStringLiteral")
public class Table extends SqlPart {

  @NotNull
  private final String mTableName;

  Table(@NotNull final Create create, @NotNull @NonNls final String tableName) {
    super(create);
    mTableName = tableName;
  }

  public Columns columns(@NotNull final ColumnInfo... columns) {
    return new Columns(this, columns);
  }

  @NotNull
  @Override
  protected String getPartSql() {
    return "TABLE " + mTableName;
  }
}