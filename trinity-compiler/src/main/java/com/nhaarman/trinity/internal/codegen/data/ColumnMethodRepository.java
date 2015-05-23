package com.nhaarman.trinity.internal.codegen.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A repository class which stores {@link ColumnMethod} instances.
 */
public class ColumnMethodRepository {

  @NotNull
  private final Map<String, ColumnMethod> mColumnMethods;

  public ColumnMethodRepository() {
    mColumnMethods = new HashMap<>();
  }

  public void store(@NotNull final ColumnMethod columnMethod) {
    String key = columnMethod.getId();
    mColumnMethods.put(key, columnMethod);
  }

  public void store(@NotNull final Collection<ColumnMethod> columnMethodes) {
    for (ColumnMethod columnMethod : columnMethodes) {
      store(columnMethod);
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

  /**
   * Tries to find the primary key setter method for the class with given fully qualified name.
   *
   * @param fullyQualifiedTableClassName The fully qualified name of the table class to find the method for.
   *
   * @return The method, or null if not found.
   */
  @Nullable
  public ColumnMethod findPrimaryKeySetter(@NotNull final String fullyQualifiedTableClassName) {
    for (ColumnMethod columnMethod : mColumnMethods.values()) {
      if (!columnMethod.isGetter() && columnMethod.isPrimary() && columnMethod.getFullyQualifiedTableClassName().equals(fullyQualifiedTableClassName)) {
        return columnMethod;
      }
    }

    return null;
  }

  /**
   * Tries to find the primary key getter method for the class with given fully qualified name.
   *
   * @param fullyQualifiedTableClassName The fully qualified name of the table class to find the method for.
   *
   * @return The method, or null if not found.
   */
  @Nullable
  public ColumnMethod findPrimaryKeyGetter(@NotNull final String fullyQualifiedTableClassName) {
    for (ColumnMethod columnMethod : mColumnMethods.values()) {
      if (columnMethod.isGetter() && columnMethod.isPrimary() && columnMethod.getFullyQualifiedTableClassName().equals(fullyQualifiedTableClassName)) {
        return columnMethod;
      }
    }

    return null;
  }

  public Collection<ColumnMethod> findForTableClass(@NotNull final String tableClassFullyQualifiedName) {
    Collection<ColumnMethod> results = new ArrayList<>();

    for (ColumnMethod columnMethod : mColumnMethods.values()) {
      if (columnMethod.getFullyQualifiedTableClassName().equals(tableClassFullyQualifiedName)) {
        results.add(columnMethod);
      }
    }

    return results;
  }
}
