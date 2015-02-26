package com.nhaarman.trinity.query.select;

import com.nhaarman.trinity.query.ExecutableQuerySqlPart;
import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;

public final class OrderBy extends ExecutableQuerySqlPart {

  @NotNull
  private final String mOrderBy;

  OrderBy(@NotNull final SqlPart previousSqlPart, @NotNull final String orderBy) {
    super(previousSqlPart);
    mOrderBy = orderBy;
  }

  public Limit limit(final String limits) {
    return new Limit(this, limits);
  }

  @Override
  @NotNull
  public String getPartSql() {
    return "ORDER BY " + mOrderBy;
  }
}
