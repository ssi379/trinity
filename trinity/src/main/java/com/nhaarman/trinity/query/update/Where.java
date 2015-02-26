package com.nhaarman.trinity.query.update;

import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.util.TextUtils.toStringArray;

public final class Where extends ExecutableUpdateQuerySqlPart {

  @NotNull
  private final String mWhere;

  @NotNull
  private final Object[] mWhereArgs;

  Where(@NotNull final SqlPart previousSqlPart, @NotNull final String where, @NotNull final Object[] args) {
    super(previousSqlPart);
    mWhere = where;
    mWhereArgs = args;
  }

  @Override
  @NotNull
  public String getPartSql() {
    return "WHERE " + mWhere;
  }

  @Override
  public String[] getPartArguments() {
    return toStringArray(mWhereArgs);
  }
}