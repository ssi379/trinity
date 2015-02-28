/*
 * Copyright 2015 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nhaarman.trinity.internal.codegen.data;

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
      if (element.getKind() == ElementKind.METHOD && element.getAnnotation(com.nhaarman.trinity.annotations.Column.class) != null) {
        ExecutableElement executableElement = (ExecutableElement) element;
        String columnName = executableElement.getAnnotation(com.nhaarman.trinity.annotations.Column.class).value();
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

  public String getEntityFullyQualifiedName() {
    return mTypeElement.getQualifiedName().toString();
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
