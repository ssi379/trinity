package com.nhaarman.trinity.internal.codegen.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TableClassRepository {

  @NotNull
  private final Map<String, TableClass> mTableClasses;

  public TableClassRepository() {
    mTableClasses = new HashMap<>();
  }

  public void save(@NotNull final TableClass tableClass) {
    String key = tableClass.getFullyQualifiedName();
    mTableClasses.put(key, tableClass);
  }

  public void save(@NotNull final Collection<TableClass> tableClasses) {
    for (TableClass tableClass : tableClasses) {
      save(tableClass);
    }
  }

  @NotNull
  public Collection<TableClass> all() {
    return mTableClasses.values();
  }

  public void clear() {
    mTableClasses.clear();
  }

  @Nullable
  public TableClass find(@NotNull final CharSequence tableClassFullyQualifiedName) {
    for (TableClass tableClass : mTableClasses.values()) {
      if (tableClass.getFullyQualifiedName().equals(tableClassFullyQualifiedName)) {
        return tableClass;
      }
    }
    return null;
  }
}