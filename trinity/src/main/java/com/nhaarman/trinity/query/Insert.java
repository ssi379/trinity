/*
 * Copyright (C) 2014 Michael Pardo
 * Copyright (C) 2014 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nhaarman.trinity.query;

import android.database.sqlite.SQLiteDatabase;
import com.nhaarman.trinity.util.TextUtils;

@SuppressWarnings({ "HardCodedStringLiteral", "PublicInnerClass" })
public final class Insert extends QueryBase {

  public Insert() {
    super(null, null);
  }

  public Into into(final String table, final String... columns) {
    return new Into(this, table, columns);
  }

  @Override
  public String getPartSql() {
    return "INSERT";
  }

  public static Insert insert() {
    return new Insert();
  }

  public static final class Into extends QueryBase {

    private final String[] mColumns;

    private Into(final Insert parent, final String table, final String... columns) {
      super(parent, table);
      mColumns = columns;
    }

    public Values values(final Object... args) {
      return new Values(this, getTableName(), args);
    }

    @Override
    protected String getPartSql() {
      StringBuilder builder = new StringBuilder(256);
      builder.append("INTO ");
      builder.append(getTableName());
      if (mColumns != null && mColumns.length > 0) {
        builder.append('(').append(TextUtils.join(",", mColumns)).append(')');
      }

      return builder.toString();
    }

    private String[] getColumns() {
      return mColumns;
    }
  }

  public static final class Values extends ExecutableQueryBase {

    private final Object[] mValuesArgs;

    private Values(final Query parent, final String table, final Object[] args) {
      super(parent, table);
      mValuesArgs = args;
    }

    @Override
    public void executeOn(final SQLiteDatabase database) {
      if (((Into) getParent()).getColumns() != null
          && ((Into) getParent()).getColumns().length != mValuesArgs.length) {
        throw new MalformedQueryException("Number of columns does not match number of values.");
      }
      super.executeOn(database);
    }

    @Override
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
}
