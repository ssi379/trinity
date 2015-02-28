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

package com.nhaarman.trinity.query;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.nhaarman.trinity.util.TextUtils.join;

public abstract class SqlPart {

  @Nullable
  private final SqlPart mPreviousSqlPart;

  protected SqlPart(@Nullable final SqlPart previousSqlPart) {
    mPreviousSqlPart = previousSqlPart;
  }

  @NotNull
  @NonNls
  protected abstract String getPartSql();

  @Nullable
  protected String[] getPartArguments() {
    return null;
  }

  @NotNull
  @NonNls
  public String getSql() {
    String result = "";
    if (mPreviousSqlPart != null) {
      result += mPreviousSqlPart.getSql() + ' ';
    }
    result += getPartSql().trim();
    return result.trim();
  }

  public String[] getArguments() {
    if (mPreviousSqlPart != null) {
      return join(mPreviousSqlPart.getArguments(), getPartArguments());
    }
    return getPartArguments();
  }

  @Nullable
  protected SqlPart getPreviousSqlPart() {
    return mPreviousSqlPart;
  }
}
