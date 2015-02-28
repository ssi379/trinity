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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ForeignKey extends ColumnInfoSqlPart {

  @NotNull
  private final String mForeignTableName;

  ForeignKey(@Nullable final SqlPart previousSqlPart, @NotNull final String foreignTableName) {
    super(previousSqlPart);
    mForeignTableName = foreignTableName;
  }

  public ColumnNames columns(@NotNull final String... columnNames) {
    return new ColumnNames(this, columnNames);
  }

  public ColumnInfo onDelete(@NotNull final OnAction onAction) {
    return new OnDelete(this, onAction);
  }

  public ColumnInfo onUpdate(@NotNull final OnAction onAction) {
    return new OnUpdate(this, onAction);
  }

  @NotNull
  @Override
  protected String getPartSql() {
    return "REFERENCES " + mForeignTableName;
  }
}
