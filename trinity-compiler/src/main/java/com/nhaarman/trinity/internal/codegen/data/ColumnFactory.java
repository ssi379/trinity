package com.nhaarman.trinity.internal.codegen.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import org.jetbrains.annotations.NotNull;

public class ColumnFactory {

  @NotNull
  private final Map<String, Column> mColumns;

  @NotNull
  private final ColumnMethodFactory mColumnMethodFactory;

  public ColumnFactory() {
    mColumns = new HashMap<>();
    mColumnMethodFactory = new ColumnMethodFactory();
  }

  /**
   * Creates Column instances from given list of elements.
   */
  @NotNull
  public Collection<Column> createColumns(@NotNull final Collection<? extends Element> elements) {
    for (Element element : elements) {
      if (isColumnMethod(element)) {
        ExecutableElement executableElement = (ExecutableElement) element;
        String columnName = findColumnName(executableElement);
        Column column = getOrCreateColumn(columnName);
        column.addMethod(mColumnMethodFactory.createColumnMethod(executableElement));
      }
    }

    return mColumns.values();
  }

  /**
   * Returns whether given Element is a method annotated with @Column.
   */
  private boolean isColumnMethod(@NotNull final Element element) {
    return element.getKind() == ElementKind.METHOD && element.getAnnotation(com.nhaarman.trinity.annotations.Column.class) != null;
  }

  /**
   * Returns the entered column name for given ExecutableElement.
   *
   * @param executableElement The ExecutableElement, which is annotated with @Column.
   */
  private String findColumnName(@NotNull final ExecutableElement executableElement) {
    return executableElement.getAnnotation(com.nhaarman.trinity.annotations.Column.class).value();
  }

  /**
   * Retrieves the already created Column with given columnName, or creates one if it doesn't exist.
   *
   * @param columnName The name of the column.
   */
  @NotNull
  private Column getOrCreateColumn(@NotNull final String columnName) {
    Column column = mColumns.get(columnName);
    if (column == null) {
      column = new Column(columnName);
      mColumns.put(columnName, column);
    }
    return column;
  }
}
