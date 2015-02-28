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
import com.nhaarman.trinity.query.ExecutableStatementSqlPart;
import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;

public final class GroupBy extends ExecutableQuerySqlPart {

  @NotNull
  private final String mGroupBy;

  GroupBy(@NotNull final SqlPart parent, @NotNull final String groupBy) {
    super(parent);
    mGroupBy = groupBy;
  }

  @NotNull
  public Having having(@NotNull final String having) {
    return new Having(this, having);
  }

  @NotNull
  public OrderBy orderBy(@NotNull final String orderBy) {
    return new OrderBy(this, orderBy);
  }

  @NotNull
  public Limit limit(@NotNull final String limits) {
    return new Limit(this, limits);
  }

  @Override
  @NotNull
  public String getPartSql() {
    return "GROUP BY " + mGroupBy;
  }
}