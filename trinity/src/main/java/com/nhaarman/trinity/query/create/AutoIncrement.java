package com.nhaarman.trinity.query.create;

import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AutoIncrement extends ColumnInfoSqlPart {

  AutoIncrement(@Nullable final SqlPart previousSqlPart) {
    super(previousSqlPart);
  }

  @NotNull
  @Override
  protected String getPartSql() {
    return "AUTOINCREMENT";
  }
}
