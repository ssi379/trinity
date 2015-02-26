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

import com.nhaarman.trinity.util.TextUtils;

@SuppressWarnings({ "HardCodedStringLiteral", "PublicInnerClass", })
public final class Select extends QueryBase {

  private final String[] mColumnNames;

  public Select(final String... columnNames) {
    super(null, null);
    mColumnNames = columnNames;
  }

  public From from(final String table) {
    return new From(this, table);
  }

  @Override
  public String getPartSql() {
    StringBuilder builder = new StringBuilder(256);
    builder.append("SELECT ");
    if (mColumnNames != null && mColumnNames.length > 0) {
      builder.append(TextUtils.join(",", mColumnNames));
    } else {
      builder.append('*');
    }

    return builder.toString();
  }

  public static Select select(final String... columnNames) {
    return new Select(columnNames);
  }

  public static final class From extends ResultQueryBase {

    private From(final Select parent, final String table) {
      super(parent, table);
    }

    public Where where(final String where, final Object... args) {
      return new Where(this, getTableName(), where, args);
    }

    public GroupBy groupBy(final String groupBy) {
      return new GroupBy(this, getTableName(), groupBy);
    }

    public OrderBy orderBy(final String orderBy) {
      return new OrderBy(this, getTableName(), orderBy);
    }

    public Limit limit(final String limit) {
      return new Limit(this, getTableName(), limit);
    }

    @Override
    public String getPartSql() {
      return "FROM " + getTableName();
    }
  }

  public static final class Where extends ResultQueryBase {

    private final String mWhere;
    private final Object[] mWhereArgs;

    private Where(final Query parent, final String table, final String where, final Object[] args) {
      super(parent, table);
      mWhere = where;
      mWhereArgs = args;
    }

    public GroupBy groupBy(final String groupBy) {
      return new GroupBy(this, getTableName(), groupBy);
    }

    public OrderBy orderBy(final String orderBy) {
      return new OrderBy(this, getTableName(), orderBy);
    }

    public Limit limit(final String limits) {
      return new Limit(this, getTableName(), limits);
    }

    @Override
    public String getPartSql() {
      return "WHERE " + mWhere;
    }

    @Override
    public String[] getPartArguments() {
      return toStringArray(mWhereArgs);
    }
  }

  public static final class GroupBy extends ResultQueryBase {

    private final String mGroupBy;

    private GroupBy(final Query parent, final String table, final String groupBy) {
      super(parent, table);
      mGroupBy = groupBy;
    }

    public Having having(final String having) {
      return new Having(this, getTableName(), having);
    }

    public OrderBy orderBy(final String orderBy) {
      return new OrderBy(this, getTableName(), orderBy);
    }

    public Limit limit(final String limits) {
      return new Limit(this, getTableName(), limits);
    }

    @Override
    public String getPartSql() {
      return "GROUP BY " + mGroupBy;
    }
  }

  public static final class Having extends ResultQueryBase {

    private final String mHaving;

    private Having(final Query parent, final String table, final String having) {
      super(parent, table);
      mHaving = having;
    }

    public OrderBy orderBy(final String orderBy) {
      return new OrderBy(this, getTableName(), orderBy);
    }

    public Limit limit(final String limits) {
      return new Limit(this, getTableName(), limits);
    }

    @Override
    public String getPartSql() {
      return "HAVING " + mHaving;
    }
  }

  public static final class OrderBy extends ResultQueryBase {

    private final String mOrderBy;

    private OrderBy(final Query parent, final String table, final String orderBy) {
      super(parent, table);
      mOrderBy = orderBy;
    }

    public Limit limit(final String limits) {
      return new Limit(this, getTableName(), limits);
    }

    @Override
    public String getPartSql() {
      return "ORDER BY " + mOrderBy;
    }
  }

  public static final class Limit extends ResultQueryBase {

    private final String mLimit;

    private Limit(final Query parent, final String table, final String limit) {
      super(parent, table);
      mLimit = limit;
    }

    public Offset offset(final String offset) {
      return new Offset(this, getTableName(), offset);
    }

    @Override
    public String getPartSql() {
      return "LIMIT " + mLimit;
    }
  }

  public static final class Offset extends ResultQueryBase {

    private final String mOffset;

    private Offset(final Query parent, final String table, final String offset) {
      super(parent, table);
      mOffset = offset;
    }

    @Override
    protected String getPartSql() {
      return "OFFSET " + mOffset;
    }
  }
}
