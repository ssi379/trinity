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
import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;

public final class OrderBy extends ExecutableQuerySqlPart {

  @NotNull
  private final String mOrderBy;

  OrderBy(@NotNull final SqlPart previousSqlPart, @NotNull final String orderBy) {
    super(previousSqlPart);
    mOrderBy = orderBy;
  }

  public Limit limit(final String limits) {
    return new Limit(this, limits);
  }

  @Override
  @NotNull
  public String getPartSql() {
    return "ORDER BY " + mOrderBy;
  }
}
