package com.nhaarman.trinity.query.select;

import com.nhaarman.trinity.query.ExecutableQuerySqlPart;
import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;

public final class Having extends ExecutableQuerySqlPart {

  @NotNull
  private final String mHaving;

  Having(@NotNull final SqlPart parent, @NotNull final String having) {
    super(parent);
    mHaving = having;
  }

  @NotNull
  public OrderBy orderBy(@NotNull final String orderBy) {
    return new OrderBy(this, orderBy);
  }

  @NotNull
  public Limit limit(@NotNull final String limits) {
    return new Limit(this, limits);
  }

  @NotNull
  @Override
  public String getPartSql() {
    return "HAVING " + mHaving;
  }
}
