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

import static com.nhaarman.trinity.util.TextUtils.toStringArray;

public final class Set extends ExecutableUpdateQuerySqlPart {

  @NotNull
  private final String mSet;

  @NotNull
  private final Object[] mSetArgs;

  Set(@NotNull final SqlPart previousSqlPart, @NotNull final String set, @NotNull final Object... args) {
    super(previousSqlPart);
    mSet = set;
    mSetArgs = args;
  }

  public Where where(final String where, final Object... args) {
    return new Where(this, where, args);
  }

  @NotNull
  @Override
  public String getPartSql() {
    return "SET " + mSet;
  }

  @Override
  public String[] getPartArguments() {
    return toStringArray(mSetArgs);
  }
}
