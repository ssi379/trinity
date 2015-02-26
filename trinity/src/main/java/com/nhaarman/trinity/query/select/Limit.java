package com.nhaarman.trinity.query.select;

import com.nhaarman.trinity.query.ExecutableQuerySqlPart;
import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;

public final class Limit extends ExecutableQuerySqlPart {

  @NotNull
  private final String mLimit;

  Limit(@NotNull final SqlPart previousSqlPart, @NotNull final String limit) {
    super(previousSqlPart);
    mLimit = limit;
  }

  public Offset offset(final String offset) {
    return new Offset(this, offset);
  }

  @Override
  @NotNull
  public String getPartSql() {
    return "LIMIT " + mLimit;
  }
}