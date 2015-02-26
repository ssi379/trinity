package com.nhaarman.trinity.query;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.nhaarman.trinity.util.TextUtils.join;

public abstract class SqlPart {

  @Nullable
  private final SqlPart mPreviousSqlPart;

  protected SqlPart(@Nullable final SqlPart previousSqlPart) {
    mPreviousSqlPart = previousSqlPart;
  }

  @NotNull
  @NonNls
  protected abstract String getPartSql();

  @Nullable
  protected String[] getPartArguments() {
    return null;
  }

  @NotNull
  @NonNls
  public String getSql() {
    String result = "";
    if (mPreviousSqlPart != null) {
      result += mPreviousSqlPart.getSql() + ' ';
    }
    result += getPartSql().trim();
    return result.trim();
  }

  public String[] getArguments() {
    if (mPreviousSqlPart != null) {
      return join(mPreviousSqlPart.getArguments(), getPartArguments());
    }
    return getPartArguments();
  }

  @Nullable
  protected SqlPart getPreviousSqlPart() {
    return mPreviousSqlPart;
  }
}
