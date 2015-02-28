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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public class RepositoryClass {

  private final TypeElement mRepositoryElement;

  private final TableClass mTableClass;

  private final Collection<RepositoryMethod> mMethods = new LinkedHashSet<>();

  public RepositoryClass(final TypeElement repositoryElement, final TableClass tableClass) {
    mRepositoryElement = repositoryElement;
    mTableClass = tableClass;

    List<? extends Element> enclosedElements = repositoryElement.getEnclosedElements();
    for (Element element : enclosedElements) {
      if (element.getKind() == ElementKind.METHOD && element.getModifiers()
          .contains(Modifier.ABSTRACT)) {
        mMethods.add(new RepositoryMethod((ExecutableElement) element));
      }
    }
  }

  public TypeElement getRepositoryElement() {
    return mRepositoryElement;
  }

  public boolean isInterface() {
    return mRepositoryElement.getKind() == ElementKind.INTERFACE;
  }

  public Collection<RepositoryMethod> getMethods() {
    return Collections.unmodifiableCollection(mMethods);
  }

  public String getPackageName() {
    return mRepositoryElement.getQualifiedName()
        .toString()
        .substring(0, mRepositoryElement.getQualifiedName().toString().lastIndexOf('.'));
  }

  public TableClass getTableClass() {
    return mTableClass;
  }

  public String getClassName() {
    return mRepositoryElement.getSimpleName().toString();
  }
}
