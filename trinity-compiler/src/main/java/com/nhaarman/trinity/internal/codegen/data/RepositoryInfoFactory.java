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
