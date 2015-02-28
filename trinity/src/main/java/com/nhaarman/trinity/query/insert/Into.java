/*
 * Copyright 2015 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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