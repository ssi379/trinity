package com.nhaarman.trinity.internal.codegen.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class ColumnMethodRepository {

  @NotNull
  private final Map<String, ColumnMethod> mColumnMethods;

  public ColumnMethodRepository() {
    mColumnMethods = new HashMap<>();
  }

  public void save(@NotNull final ColumnMethod columnMethod) {
    String key = columnMethod.getId();
    mColumnMethods.put(key, columnMethod);
  }

  public void save(@NotNull final Collection<ColumnMethod> columnMethodes) {
    for (ColumnMethod columnMethod : columnMethodes) {
      save(columnMethod);
    }
  }

  @NotNull
  public Collection<ColumnMethod> findGettersForTableClass(@NotNull final String tableClassFullyQualifiedName) {
    Collection<ColumnMethod> results = new ArrayList<>();

    for (ColumnMethod columnMethod : mColumnMethods.values()) {
      if (columnMethod.getFullyQualifiedTableClassName().equals(tableClassFullyQualifiedName) && columnMethod.isGetter()) {
        results.add(columnMethod);
      }
    }

    return results;
  }

  @NotNull
  public Collection<ColumnMethod> findSettersForTableClass(@NotNull final String tableClassFullyQualifiedName) {
    Collection<ColumnMethod> results = new ArrayList<>();

    for (ColumnMethod columnMethod : mColumnMethods.values()) {
      if (columnMethod.getFullyQualifiedTableClassName().equals(tableClassFullyQualifiedName) && !columnMethod.isGetter()) {
        results.add(columnMethod);
      }
    }

    return results;
  }

  public void clear() {
    mColumnMethods.clear();
  }
}
