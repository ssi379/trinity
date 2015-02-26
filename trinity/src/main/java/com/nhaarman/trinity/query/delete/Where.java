package com.nhaarman.trinity.query.delete;

import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.util.TextUtils.toStringArray;

public final class Where extends ExecutableDeleteQuerySqlPart {

  @NotNull
  private final String mWhere;

  @NotNull
  private final Object[] mWhereArgs;

  Where(final SqlPart parent, @NotNull final String where, @NotNull final Object[] args) {
    super(parent);
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