package com.nhaarman.trinity.query.update;

import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.util.TextUtils.toStringArray;

public final class Set extends ExecutableUpdateQuerySqlPart {

  @NotNull
  private final String mSet;

  @NotNull
  private final Object[] mSetArgs;

  Set(@NotNull final SqlPart previousSqlPart, @NotNull final String set, @NotNull final Object... args) {
    super(previousSqlPart);
    mSet = set;
    mSetArgs = args;
  }

  public Where where(final String where, final Object... args) {
    return new Where(this, where, args);
  }

  @NotNull
  @Override
  public String getPartSql() {
    return "SET " + mSet;
  }

  @Override
  public String[] getPartArguments() {
    return toStringArray(mSetArgs);
  }
}
