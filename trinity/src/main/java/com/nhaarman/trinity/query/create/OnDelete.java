package com.nhaarman.trinity.query.create;

import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OnDelete extends ColumnInfoSqlPart {

  @NotNull
  private final OnAction mOnAction;

  OnDelete(@Nullable final SqlPart previousSqlPart, @NotNull final OnAction onAction) {
    super(previousSqlPart);
    mOnAction = onAction;
  }

  @NotNull
  @Override
  protected String getPartSql() {
    return "ON DELETE " + mOnAction;
  }
}
