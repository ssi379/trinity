package com.nhaarman.trinity.query.select;

import com.nhaarman.trinity.query.ExecutableQuerySqlPart;
import com.nhaarman.trinity.query.ExecutableStatementSqlPart;
import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;

public final class GroupBy extends ExecutableQuerySqlPart {

  @NotNull
  private final String mGroupBy;

  GroupBy(@NotNull final SqlPart parent, @NotNull final String groupBy) {
    super(parent);
    mGroupBy = groupBy;
  }

  @NotNull
  public Having having(@NotNull final String having) {
    return new Having(this, having);
  }

  @NotNull
  public OrderBy orderBy(@NotNull final String orderBy) {
    return new OrderBy(this, orderBy);
  }

  @NotNull
  public Limit limit(@NotNull final String limits) {
    return new Limit(this, limits);
  }

  @Override
  @NotNull
  public String getPartSql() {
    return "GROUP BY " + mGroupBy;
  }
}