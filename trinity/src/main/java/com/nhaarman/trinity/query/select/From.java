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

import com.nhaarman.trinity.query.ExecutableQuerySqlPart;
import org.jetbrains.annotations.NotNull;

public final class From extends ExecutableQuerySqlPart {

  @NotNull
  private final String mTableName;

  From(@NotNull final Select parent, @NotNull final String tableName) {
    super(parent);
    mTableName = tableName;
  }

  @NotNull
  public Where where(@NotNull final String where, @NotNull final Object... args) {
    return new Where(this, where, args);
  }

  @NotNull
  public GroupBy groupBy(@NotNull final String groupBy) {
    return new GroupBy(this, groupBy);
  }

  @NotNull
  public OrderBy orderBy(@NotNull final String orderBy) {
    return new OrderBy(this, orderBy);
  }

  @NotNull
  public Limit limit(@NotNull final String limit) {
    return new Limit(this, limit);
  }

  @Override
  @NotNull
  public String getPartSql() {
    return "FROM " + mTableName;
  }
}
