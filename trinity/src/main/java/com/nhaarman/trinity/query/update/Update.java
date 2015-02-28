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

package com.nhaarman.trinity.query.update;

import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;

public final class Update extends SqlPart {

  @NotNull
  private final String mTableName;

  public Update(@NotNull final String tableName) {
    super(null);
    mTableName = tableName;
  }

  public Set set(@NotNull final String set, @NotNull final Object... args) {
    return new Set(this, set, args);
  }

  @NotNull
  @Override
  public String getPartSql() {
    return "UPDATE " + mTableName;
  }
}
