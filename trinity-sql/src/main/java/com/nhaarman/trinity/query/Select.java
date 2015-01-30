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

import android.text.TextUtils;
import com.nhaarman.trinity.query.Select.Join.Type;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"HardCodedStringLiteral", "PublicInnerClass"})
public final class Select extends QueryBase {

  private final String[] mColumns;

  public Select() {
    this(new String[] {});
  }

  public Select(final String... columns) {
    super(null, null);
    mColumns = columns;
  }

  public From from(final String table) {
    return new From(this, table);
  }

  @Override
  public String getPartSql() {
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    if (mColumns != null && mColumns.length > 0) {
      builder.append(TextUtils.join(", ", mColumns)).append(" ");
    } else {
      builder.append("* ");
    }

    return builder.toString();
  }

  public static final class From extends ResultQueryBase {

    private final List<Join> mJoins = new ArrayList<>();

    private From(final Select parent, final String table) {
      super(parent, table);
    }

    public Join join(final String table) {
      return addJoin(table, Type.JOIN);
    }

    public Join leftJoin(final String table) {
      return addJoin(table, Type.LEFT);
    }

    public Join leftOuterJoin(final String table) {
      return addJoin(table, Type.LEFT_OUTER);
    }

    public Join innerJoin(final String table) {
      return addJoin(table, Type.INNER);
    }

    public Join crossJoin(final String table) {
      return addJoin(table, Type.CROSS);
    }

    public Join naturalJoin(final String table) {
      return addJoin(table, Type.NATURAL_JOIN);
    }

    public Join naturalLeftJoin(final String table) {
      return addJoin(table, Type.NATURAL_LEFT);
    }

    public Join naturalLeftOuterJoin(final String table) {
      return addJoin(table, Type.NATURAL_LEFT_OUTER);
    }

    public Join naturalInnerJoin(final String table) {
      return addJoin(table, Type.NATURAL_INNER);
    }

    public Join naturalCrossJoin(final String table) {
      return addJoin(table, Type.NATURAL_CROSS);
    }

    public Where where(final String where) {
      return new Where(this, getTable(), where, null);
    }

    public Where where(final String where, final Object... args) {
      return new Where(this, getTable(), where, args);
    }

    public GroupBy groupBy(final String groupBy) {
      return new GroupBy(this, getTable(), groupBy);
    }

    public OrderBy orderBy(final String orderBy) {
      return new OrderBy(this, getTable(), orderBy);
    }

    public Limit limit(final String limit) {
      return new Limit(this, getTable(), limit);
    }

    private Join addJoin(final String table, final Type type) {
      final Join join = new Join(this, table, type);
      mJoins.add(join);
      return join;
    }

    @Override
    public String getPartSql() {
      StringBuilder builder = new StringBuilder();
      builder.append("FROM ");
      builder.append(getTable()).append(" ");

      for (Join join : mJoins) {
        builder.append(join.getPartSql()).append(" ");
      }

      return builder.toString();
    }
  }

  public static final class Join extends QueryBase {

    private final Type mType;
    private String mConstraint;

    private Join(final From parent, final String table, final Type type) {
      super(parent, table);
      mType = type;
    }

    public From on(final String constraint) {
      mConstraint = "ON " + constraint;
      return (From) getParent();
    }

    public From using(final String... columns) {
      mConstraint = "USING (" + TextUtils.join(", ", columns) + ")";
      return (From) getParent();
    }

    @Override
    public String getPartSql() {
      return mType.getKeyword() + " " + getTable() + " " + mConstraint;
    }

    public enum Type {
      JOIN("JOIN"),
      LEFT("LEFT JOIN"),
      LEFT_OUTER("LEFT OUTER JOIN"),
      INNER("INNER JOIN"),
      CROSS("CROSS JOIN"),
      NATURAL_JOIN("NATURAL JOIN"),
      NATURAL_LEFT("NATURAL LEFT JOIN"),
      NATURAL_LEFT_OUTER("NATURAL LEFT OUTER JOIN"),
      NATURAL_INNER("NATURAL INNER JOIN"),
      NATURAL_CROSS("NATURAL CROSS JOIN");

      private final String mKeyword;

      Type(final String keyword) {
        mKeyword = keyword;
      }

      public String getKeyword() {
        return mKeyword;
      }
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

    public GroupBy groupBy() {
      return null;
    }

    public OrderBy orderBy(final String orderBy) {
      return new OrderBy(this, getTable(), orderBy);
    }

    public Limit limit(final String limits) {
      return new Limit(this, getTable(), limits);
    }

    @Override
    public String getPartSql() {
      return "WHERE " + mWhere;
    }

    @Override
    public String[] getPartArgs() {
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
      return new Having(this, getTable(), having);
    }

    public OrderBy orderBy(final String orderBy) {
      return new OrderBy(this, getTable(), orderBy);
    }

    public Limit limit(final String limits) {
      return new Limit(this, getTable(), limits);
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
      return new OrderBy(this, getTable(), orderBy);
    }

    public Limit limit(final String limits) {
      return new Limit(this, getTable(), limits);
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
      return new Limit(this, getTable(), limits);
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
      return new Offset(this, getTable(), offset);
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
