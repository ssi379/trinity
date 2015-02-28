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

import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("HardCodedStringLiteral")
public class Table extends SqlPart {

  @NotNull
  private final String mTableName;

  Table(@NotNull final Create create, @NotNull @NonNls final String tableName) {
    super(create);
    mTableName = tableName;
  }

  public Columns columns(@NotNull final ColumnInfo... columns) {
    return new Columns(this, columns);
  }

  @NotNull
  @Override
  protected String getPartSql() {
    return "TABLE " + mTableName;
  }
}