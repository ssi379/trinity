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

public abstract class QueryBase implements Query {

  private final Query mParent;
  private final String mTable;

  protected QueryBase(final Query parent, final String table) {
    mParent = parent;
    mTable = table;
  }

  @Override
  public final String getSql() {
    if (mParent != null) {
      return mParent.getSql() + ' ' + getPartSql().trim();
    }
    return getPartSql().trim();
  }

  @Override
  public final String[] getArgs() {
    if (mParent != null) {
      return join(mParent.getArgs(), getPartArguments());
    }
    return getPartArguments();
  }

  public Query getParent() {
    return mParent;
  }

  public String getTableName() {
    return mTable;
  }

  protected String getPartSql() {
    return null;
  }

  protected String[] getPartArguments() {
    return null;
  }

  protected final String[] toStringArray(final Object[] array) {
    if (array == null) {
      return null;
    }
    final String[] transformedArray = new String[array.length];
    for (int i = 0; i < array.length; i++) {
      transformedArray[i] = String.valueOf(array[i]);
    }
    return transformedArray;
  }

  private String[] join(final String[] array1, final String... array2) {
    if (array1 == null) {
      return clone(array2);
    }
    if (array2 == null) {
      return clone(array1);
    }

    final String[] joinedArray = new String[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  private String[] clone(final String[] array) {
    if (array == null) {
      return null;
    }
    return array.clone();
  }
}
