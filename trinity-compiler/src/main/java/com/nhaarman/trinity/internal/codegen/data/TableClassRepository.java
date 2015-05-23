package com.nhaarman.trinity.internal.codegen.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/**
 * A repository class which stores instances of {@link TableClass}.
 */
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
}