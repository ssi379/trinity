package com.nhaarman.trinity.query.create;

import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Default extends ColumnInfoSqlPart {

  public static final String NULL = "NULL";
  public static final String CURRENT_TIME = "CURRENT_TIME";
  public static final String CURRENT_DATE = "CURRENT_DATE";
  public static final String CURRENT_TIMESTAMP = "CURRENT_TIMESTAMP";

  @NotNull
  private final Object mValue;

  Default(@Nullable final SqlPart previousSqlPart, @NotNull final Object value) {
    super(previousSqlPart);
    mValue = value;
  }

  @NotNull
  @Override
  protected String getPartSql() {
    return "DEFAULT " + mValue;
  }
}
