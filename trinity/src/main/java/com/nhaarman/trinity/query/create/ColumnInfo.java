package com.nhaarman.trinity.query.create;

import org.jetbrains.annotations.NotNull;

public interface ColumnInfo {

  PrimaryKey primaryKey();

  NotNullConstraint notNull();

  Unique unique();

  Default defaultValue(@NotNull final Object defaultValue);

  ForeignKey references(@NotNull final String foreignTableName);

  @NotNull
  String getSql();
}
