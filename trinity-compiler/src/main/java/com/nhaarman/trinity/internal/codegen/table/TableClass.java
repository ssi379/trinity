package com.nhaarman.trinity.internal.codegen.table;

import com.nhaarman.trinity.annotations.Table;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

public class TableClass {

  private final Collection<Column> mColumns;

  private final TypeElement mTypeElement;

  public TableClass(final TypeElement typeElement) {
    mTypeElement = typeElement;

    Map<String, Column> columns = new HashMap<>();
    for (Element element : typeElement.getEnclosedElements()) {
      if (element.getKind() == ElementKind.METHOD
          && element.getAnnotation(com.nhaarman.trinity.annotations.Column.class) != null) {
        ExecutableElement executableElement = (ExecutableElement) element;
        String columnName =
            executableElement.getAnnotation(com.nhaarman.trinity.annotations.Column.class).value();
        Column column = columns.get(columnName);
        if (column == null) {
          column = new Column(columnName);
          columns.put(columnName, column);
        }
        column.addExecutableElement(executableElement);
      }
    }
    mColumns = columns.values();
  }

  public String getTableName() {
    return mTypeElement.getAnnotation(Table.class).name();
  }

  public Collection<Column> getColumns() {
    return Collections.unmodifiableCollection(mColumns);
  }

  public TypeElement getTypeElement() {
    return mTypeElement;
  }

  public AnnotationMirror getTableAnnotationMirror() {
    AnnotationMirror result = null;

    List<? extends AnnotationMirror> annotationMirrors = mTypeElement.getAnnotationMirrors();
    for (AnnotationMirror annotationMirror : annotationMirrors) {
      if (annotationMirror.getAnnotationType().toString().equals(Table.class.getCanonicalName())) {
        result = annotationMirror;
      }
    }
    return result;
  }

  public TypeElement getEntityTypeElement() {
    return mTypeElement;
  }

  public Column getPrimaryKeyColumn() {
    for (Column column : mColumns) {
      if (column.isPrimary()) {
        return column;
      }
    }
    return null;
  }
}
