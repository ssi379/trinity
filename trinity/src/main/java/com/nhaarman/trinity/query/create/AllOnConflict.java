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

class AllOnConflict extends ColumnInfoSqlPart implements PrimaryKeyOnConflict, NotNullOnConflict, UniqueOnConflict {

  @NotNull
  private final ConflictAction mAction;

  AllOnConflict(@NotNull final SqlPart sqlPart, @NotNull final ConflictAction action) {
    super(sqlPart);
    mAction = action;
  }

  @Override
  public AutoIncrement autoIncrement() {
    return new AutoIncrement(this);
  }

  @Override
  public Default defaultValue(@NotNull final Object defaultValue) {
    return new Default(this, defaultValue);
  }

  @Override
  public ForeignKey references(@NotNull final String foreignTableName) {
    return new ForeignKey(this, foreignTableName);
  }

  public PrimaryKeyOnConflict onConflict(@NotNull final ConflictAction action) {
    return new AllOnConflict(this, action);
  }

  @NotNull
  @Override
  protected String getPartSql() {
    return "ON CONFLICT " + mAction;
  }
}
