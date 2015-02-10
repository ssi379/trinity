package com.nhaarman.trinity.internal.codegen.data;

import java.util.Collection;
import java.util.LinkedHashSet;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Column {

  @NotNull
  private final String mColumnName;

  @NotNull
  private final Collection<ColumnMethod> mMethods = new LinkedHashSet<>();

  public Column(@NotNull final String columnName) {
    mColumnName = columnName;
  }

  public void addExecutableElement(@NotNull final ExecutableElement executableElement) {
    mMethods.add(new ColumnMethod(executableElement));
  }

  @Nullable
  public ColumnMethod getter() {
    for (ColumnMethod method : mMethods) {
      if (method.isGetter()) {
        return method;
      }
    }

    return null;
  }

  @Nullable
  public ColumnMethod setter() {
    for (ColumnMethod method : mMethods) {
      if (method.isSetter()) {
        return method;
      }
    }

    return null;
  }

  /**
   * Returns the fully qualified Java type of the column.
   *
   * @return The type, e.g. "java.lang.String".
   */
  @NotNull
  public String getFullyQualifiedJavaType() {
    if (mMethods.isEmpty()) {
      throw new IllegalStateException("No methods for this column."); // TODO: Proper exception handling.
    }

    return mMethods.iterator().next().getType().toString();
  }

  @NotNull
  public Element getElement() {
    return mMethods.iterator().next().getElement();
  }

  @NotNull
  public String getName() {
    return mColumnName;
  }

  public boolean isPrimary() {
    for (ColumnMethod method : mMethods) {
      if (method.isPrimary()) {
        return true;
      }
    }
    return false;
  }
}
