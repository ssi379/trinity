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

public class Default extends ColumnInfoSqlPart {

  public static final String NULL = "NULL";
  public static final String CURRENT_TIME = "CURRENT_TIME";
  public static final String CURRENT_DATE = "CURRENT_DATE";
  public static final String CURRENT_TIMESTAMP = "CURRENT_TIMESTAMP";

  @NotNull
  private final Object mValue;

  Default(@Nullable final SqlPart previousSqlPart, @NotNull final Object value) {
    super(previousSqlPart);
    mValue = value;
  }

  @NotNull
  @Override
  protected String getPartSql() {
    return "DEFAULT " + mValue;
  }
}
