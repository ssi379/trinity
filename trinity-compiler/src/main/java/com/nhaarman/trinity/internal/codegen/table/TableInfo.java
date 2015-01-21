package com.nhaarman.trinity.internal.codegen.table;

import com.nhaarman.trinity.internal.codegen.column.ColumnInfo;
import com.nhaarman.trinity.annotations.Table;
import com.squareup.javapoet.ParameterSpec;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;

public class TableInfo {

  private Map<String, ColumnInfo> mColumns;

  private String mEntityFQN;

  private String mPackageName;

  private String mTableName;

  private TypeElement mElement;

  public String getTableName() {
    return mTableName;
  }

  public void setTableName(final String tableName) {
    mTableName = tableName;
  }

  public String getPackageName() {
    return mPackageName;
  }

  public void setPackageName(final String packageName) {
    mPackageName = packageName;
  }

  public String getEntityFQN() {
    return mEntityFQN;
  }

  public void setEntityFQN(final String entityFQN) {
    mEntityFQN = entityFQN;
  }

  public Collection<ColumnInfo> getColumns() {
    return mColumns.values();
  }

  public void setColumns(final Map<String, ColumnInfo> columns) {
    mColumns = columns;
  }

  public TypeElement getElement() {
    return mElement;
  }

  public void setElement(final TypeElement element) {
    mElement = element;
  }

  public AnnotationMirror getTableAnnotationMirror() {
    AnnotationMirror result = null;

    List<? extends AnnotationMirror> annotationMirrors = mElement.getAnnotationMirrors();
    for (AnnotationMirror annotationMirror : annotationMirrors) {
      if (annotationMirror.getAnnotationType().toString().equals(Table.class.getCanonicalName())) {
        result = annotationMirror;
      }
    }
    return result;
  }

  public TypeElement getEntityTypeElement() {
    return mElement;
  }
}
