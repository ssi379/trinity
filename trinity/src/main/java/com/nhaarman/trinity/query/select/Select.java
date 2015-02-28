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

package com.nhaarman.trinity.query.select;

import com.nhaarman.trinity.query.SqlStart;
import org.jetbrains.annotations.NotNull;

public final class Select extends SqlStart {

  @NotNull
  private final String[] mColumnNames;

  public Select(@NotNull final String... columnNames) {
    mColumnNames = columnNames;
  }

  @NotNull
  public From from(final String table) {
    return new From(this, table);
  }

  @Override
  @NotNull
  public String getPartSql() {
    StringBuilder builder = new StringBuilder(256);
    builder.append("SELECT ");
    if (mColumnNames.length > 0) {
      for (int i = 0; i < mColumnNames.length; i++) {
        builder.append(mColumnNames[i]);
        if (i + 1 < mColumnNames.length) {
          builder.append(',');
        }
      }
    } else {
      builder.append('*');
    }

    return builder.toString();
  }

  public static Select select(final String... columnNames) {
    return new Select(columnNames);
  }
}
