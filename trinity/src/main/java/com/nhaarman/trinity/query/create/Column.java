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

import com.nhaarman.trinity.query.SqlStart;
import org.jetbrains.annotations.NotNull;

public class Column extends SqlStart implements ColumnInfo {

  @NotNull
  private final String mColumnName;

  @NotNull
  private final Type mType;

  Column(@NotNull final String columnName, @NotNull final Type type) {
    mColumnName = columnName;
    mType = type;
  }

  @NotNull
  @Override
  protected String getPartSql() {
    return mColumnName + ' ' + mType;
  }

  @Override
  public PrimaryKey primaryKey() {
    return new PrimaryKey(this);
  }

  @Override
  public NotNullConstraint notNull() {
    return new NotNullConstraint(this);
  }

  @Override
  public Unique unique() {
    return new Unique(this);
  }

  @Override
  public Default defaultValue(@NotNull final Object defaultValue) {
    return new Default(this, defaultValue);
  }

  @Override
  public ForeignKey references(@NotNull final String foreignTableName) {
    return new ForeignKey(this, foreignTableName);
  }

  public static Column integer(final String columnName) {
    return new Column(columnName, Type.INTEGER);
  }

  public static Column text(final String columnName) {
    return new Column(columnName, Type.TEXT);
  }

  public static Column none(final String columnName) {
    return new Column(columnName, Type.NONE);
  }

  public static Column real(final String columnName) {
    return new Column(columnName, Type.REAL);
  }

  public static Column numeric(final String columnName) {
    return new Column(columnName, Type.NUMERIC);
  }

  private enum Type {
    INTEGER, TEXT, NONE, REAL, NUMERIC
  }
}
