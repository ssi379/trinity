package com.nhaarman.trinity.query.insert;

import com.nhaarman.trinity.query.SqlPart;
import com.nhaarman.trinity.util.TextUtils;
import org.jetbrains.annotations.NotNull;

public final class Into extends SqlPart {

  @NotNull
  private final String mTableName;

  @NotNull
  private final String[] mColumns;

  Into(@NotNull final SqlPart parent, @NotNull final String tableName, @NotNull final String... columns) {
    super(parent);
    mTableName = tableName;
    mColumns = columns;
  }

  public Values values(final Object... args) {
    return new Values(this, args);
  }

  @Override
  @NotNull
  protected String getPartSql() {
    StringBuilder builder = new StringBuilder(256);
    builder.append("INTO ");
    builder.append(mTableName);
    if (mColumns.length > 0) {
      builder.append('(').append(TextUtils.join(",", mColumns)).append(')');
    }

    return builder.toString();
  }

  @NotNull
  String[] getColumns() {
    return mColumns;
  }
}