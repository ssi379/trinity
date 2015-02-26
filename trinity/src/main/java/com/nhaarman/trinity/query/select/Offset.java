package com.nhaarman.trinity.query.select;

import com.nhaarman.trinity.query.ExecutableQuerySqlPart;
import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;

public final class Offset extends ExecutableQuerySqlPart {

  @NotNull
  private final String mOffset;

  Offset(@NotNull final SqlPart previousSqlPart, @NotNull final String offset) {
    super(previousSqlPart);
    mOffset = offset;
  }

  @NotNull
  @Override
  protected String getPartSql() {
    return "OFFSET " + mOffset;
  }
}