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

package com.nhaarman.trinity.query.create;

import com.nhaarman.trinity.query.ExecutableStatementSqlPart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Columns extends ExecutableStatementSqlPart {

  @NotNull
  private final ColumnInfo[] mColumns;

  Columns(@Nullable final Table table, @NotNull final ColumnInfo... columns) {
    super(table);
    mColumns = columns;
  }

  @NotNull
  @Override
  protected String getPartSql() {
    if (mColumns.length == 0) {
      return "";
    }

    StringBuilder stringBuilder = new StringBuilder(256);

    stringBuilder.append('(');
    for (int i = 0; i < mColumns.length; i++) {
      stringBuilder.append(mColumns[i].getSql());
      if (i + 1 < mColumns.length) {
        stringBuilder.append(',');
      }
    }
    stringBuilder.append(')');

    return stringBuilder.toString();
  }
}
