package com.nhaarman.trinity.internal.codegen.data;

import java.util.Collection;
import java.util.LinkedHashSet;
import javax.lang.model.element.ExecutableElement;

public class Column {

  private final String mColumnName;

  private final Collection<ColumnMethod> mMethods = new LinkedHashSet<>();

  public Column(final String columnName) {
    mColumnName = columnName;
  }

  public void addExecutableElement(final ExecutableElement executableElement) {
    mMethods.add(new ColumnMethod(executableElement));
  }

  public ColumnMethod getter() {
    for (ColumnMethod method : mMethods) {
      if (method.isGetter()) {
        return method;
      }
    }

    return null;
  }

  public ColumnMethod setter() {
    for (ColumnMethod method : mMethods) {
      if (method.isSetter()) {
        return method;
      }
    }

    return null;
  }

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
