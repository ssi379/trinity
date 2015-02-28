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

package com.nhaarman.trinity.query.insert;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nhaarman.trinity.query.MalformedQueryException;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.util.TextUtils.toStringArray;

public final class Values extends ExecutableInsertQuerySqlPart {

  @NotNull
  private final Into mInto;

  @NotNull
  private final Object[] mValuesArgs;

  Values(@NotNull final Into into, @NotNull final Object[] args) {
    super(into);
    mInto = into;
    mValuesArgs = args;
  }

  @Override
  @NotNull
  public Cursor queryOn(@NotNull final SQLiteDatabase database) {
    if (mInto.getColumns().length != mValuesArgs.length) {
      throw new MalformedQueryException("Number of columns does not match number of values.");
    }
    return super.queryOn(database);
  }

  @Override
  @NotNull
  protected String getPartSql() {
    StringBuilder builder = new StringBuilder(256);
    builder.append("VALUES(");
    for (int i = 0; i < mValuesArgs.length; i++) {
      if (i > 0) {
        builder.append(',');
      }
      builder.append('?');
    }
    builder.append(')');
    return builder.toString();
  }

  @Override
  protected String[] getPartArguments() {
    return toStringArray(mValuesArgs);
  }
}