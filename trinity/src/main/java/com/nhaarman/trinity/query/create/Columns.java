package com.nhaarman.trinity.query.create;

import com.nhaarman.trinity.query.ExecutableStatementSqlPart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Columns extends ExecutableStatementSqlPart {

  @NotNull
  private final ColumnInfo[] mColumns;

  Columns(@Nullable final Table table, @NotNull final ColumnInfo... columns) {
    super(table);
    mColumns = columns;
  }

  @NotNull
  @Override
  protected String getPartSql() {
    if (mColumns.length == 0) {
      return "";
    }

    StringBuilder stringBuilder = new StringBuilder(256);

    stringBuilder.append('(');
    for (int i = 0; i < mColumns.length; i++) {
      stringBuilder.append(mColumns[i].getSql());
      if (i + 1 < mColumns.length) {
        stringBuilder.append(',');
      }
    }
    stringBuilder.append(')');

    return stringBuilder.toString();
  }
}
