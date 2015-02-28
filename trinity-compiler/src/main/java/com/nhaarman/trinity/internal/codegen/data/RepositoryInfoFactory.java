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

import com.nhaarman.trinity.annotations.Repository;
import java.util.HashSet;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import org.jetbrains.annotations.NotNull;

public class RepositoryInfoFactory {

  public Set<? extends RepositoryClass> createRepositoryInfo(@NotNull final Set<? extends Element> repositoryElements,
                                                             @NotNull final Set<? extends TableClass> tableClasses) {
    Set<RepositoryClass> results = new HashSet<>();

    for (Element repositoryElement : repositoryElements) {
      TypeElement typeElement = (TypeElement) repositoryElement;
      TableClass tableClass = findTableInfo(tableClasses, typeElement);
      results.add(new RepositoryClass(typeElement, tableClass));
    }

    return results;
  }

  private TableClass findTableInfo(@NotNull final Set<? extends TableClass> tableClasses,
                                   @NotNull final TypeElement typeElement) {
    for (TableClass tableClass : tableClasses) {
      if (tableClass.getEntityTypeElement()
          .toString()
          .equals(getRepositoryValue(typeElement).toString())) {
        return tableClass;
      }
    }
    return null;
  }

  private TypeMirror getRepositoryValue(final TypeElement typeElement) {
    try {
      typeElement.getAnnotation(Repository.class).value();
      return null;
    } catch (MirroredTypeException e) {
      return e.getTypeMirror();
    }
  }
}
