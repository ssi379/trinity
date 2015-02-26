package com.nhaarman.trinity.query.create;

import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ColumnNames extends ColumnInfoSqlPart {

  @NotNull
  private final String[] mColumnNames;

  protected ColumnNames(@Nullable final SqlPart previousSqlPart, @NotNull final String[] columnNames) {
    super(previousSqlPart);
    mColumnNames = columnNames;
  }

  public OnDelete onDelete(@NotNull final OnAction onAction) {
    return new OnDelete(this, onAction);
  }

  public OnUpdate onUpdate(@NotNull final OnAction onAction) {
    return new OnUpdate(this, onAction);
  }

  @NotNull
  @Override
  protected String getPartSql() {
    if (mColumnNames.length == 0) {
      return "";
    }

    StringBuilder stringBuilder = new StringBuilder(256);
    stringBuilder.append('(');
    for (int i = 0; i < mColumnNames.length; i++) {
      stringBuilder.append(mColumnNames[i]);
      if (i + 1 < mColumnNames.length) {
        stringBuilder.append(',');
      }
    }
    stringBuilder.append(')');
    return stringBuilder.toString();
  }
}
