package com.nhaarman.trinity.query.select;

import com.nhaarman.trinity.query.ExecutableQuerySqlPart;
import org.jetbrains.annotations.NotNull;

public final class From extends ExecutableQuerySqlPart {

  @NotNull
  private final String mTableName;

  From(@NotNull final Select parent, @NotNull final String tableName) {
    super(parent);
    mTableName = tableName;
  }

  @NotNull
  public Where where(@NotNull final String where, @NotNull final Object... args) {
    return new Where(this, where, args);
  }

  @NotNull
  public GroupBy groupBy(@NotNull final String groupBy) {
    return new GroupBy(this, groupBy);
  }

  @NotNull
  public OrderBy orderBy(@NotNull final String orderBy) {
    return new OrderBy(this, orderBy);
  }

  @NotNull
  public Limit limit(@NotNull final String limit) {
    return new Limit(this, limit);
  }

  @Override
  @NotNull
  public String getPartSql() {
    return "FROM " + mTableName;
  }
}
