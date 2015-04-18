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
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass.Builder;
import com.nhaarman.trinity.internal.codegen.method.RepositoryMethod;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import org.jetbrains.annotations.NotNull;

public class RepositoryClassFactory {

  public Collection<RepositoryClass> createRepositoryClasses(@NotNull final Set<? extends Element> repositoryElements) {
    Set<RepositoryClass> results = new HashSet<>();

    for (Element repositoryElement : repositoryElements) {
      TypeElement typeElement = (TypeElement) repositoryElement;
      results.add(createRepositoryClass(typeElement));
    }

    return results;
  }

  @NotNull
  private RepositoryClass createRepositoryClass(@NotNull final TypeElement typeElement) {
    Builder builder = new Builder();

    String className = typeElement.getSimpleName().toString();
    String packageName = typeElement.getQualifiedName().toString().substring(0, typeElement.getQualifiedName().toString().indexOf(className) - 1);

    builder.withClassName(className);
    builder.withPackageName(packageName);

    TypeMirror repositoryValue = getRepositoryValue(typeElement);
    String tableClassName = repositoryValue.toString().substring(repositoryValue.toString().lastIndexOf('.') + 1);
    String tablePackageName = repositoryValue.toString().substring(0, repositoryValue.toString().indexOf(tableClassName) - 1);

    builder.withTableClassName(tableClassName);
    builder.withTableClassPackageName(tablePackageName);

    if (typeElement.getKind() == ElementKind.INTERFACE) {
      builder.isInterface();
    }

    RepositoryMethodFactory repositoryMethodFactory = new RepositoryMethodFactory();
    Collection<RepositoryMethod> repositoryMethods = new HashSet<>();
    List<? extends Element> enclosedElements = typeElement.getEnclosedElements();
    for (Element element : enclosedElements) {
      if (element.getKind() == ElementKind.METHOD && element.getModifiers().contains(Modifier.ABSTRACT)) {
        repositoryMethods.add(repositoryMethodFactory.createRepositoryMethod((ExecutableElement) element));
      }
    }
    builder.withMethods(repositoryMethods);

    builder.withElement(typeElement);

    return builder.build();
  }

  @NotNull
  private TypeMirror getRepositoryValue(final TypeElement typeElement) {
    try {
      typeElement.getAnnotation(Repository.class).value();
      throw new IllegalStateException("Could not get repository value");
    } catch (MirroredTypeException e) {
      return e.getTypeMirror();
    }
  }
}
