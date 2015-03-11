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
import com.nhaarman.trinity.internal.codegen.data.TableClass.Builder;
import java.util.HashSet;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import org.jetbrains.annotations.NotNull;

public class TableClassFactory {

  public Set<TableClass> createTableClasses(@NotNull final Set<? extends Element> tableElements) {
    Set<TableClass> tableClasses = new HashSet<>(tableElements.size());

    for (Element tableElement : tableElements) {
      tableClasses.add(createTableClass((TypeElement) tableElement));
    }

    return tableClasses;
  }

  public TableClass createTableClass(@NotNull final TypeElement tableElement) {
    Builder builder = new Builder();

    String className = tableElement.getSimpleName().toString();
    String packageName = tableElement.getQualifiedName().toString().substring(0, tableElement.getQualifiedName().toString().indexOf(className) - 1);

    builder.withTableName(tableElement.getAnnotation(Table.class).name());
    builder.withClassName(className);
    builder.withPackageName(packageName);
    builder.withColumns(new ColumnFactory().createColumns(tableElement.getEnclosedElements()));
    builder.withElement(tableElement);

    return builder.build();
  }
}
