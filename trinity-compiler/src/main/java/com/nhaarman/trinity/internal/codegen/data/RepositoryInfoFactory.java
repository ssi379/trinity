package com.nhaarman.trinity.internal.codegen.data;

import com.nhaarman.trinity.annotations.Repository;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;

public class RepositoryInfoFactory {

  public Collection<RepositoryClass> createRepositoryInfo(
      final Set<? extends Element> repositoryElements,
      final Collection<TableClass> tableClasses,
      final RoundEnvironment roundEnvironment) {
    Set<RepositoryClass> results = new HashSet<>();

    for (Element repositoryElement : repositoryElements) {
      TypeElement typeElement = (TypeElement) repositoryElement;
      TableClass tableClass = findTableInfo(tableClasses, typeElement);
      results.add(new RepositoryClass(typeElement, tableClass));
    }

    return results;
  }

  private TableClass findTableInfo(final Collection<TableClass> tableClasses,
      final TypeElement typeElement) {
    for (TableClass tableClass : tableClasses) {
      if (tableClass.getEntityTypeElement()
          .toString()
          .equals(getRepositoryValue(typeElement).toString())) {
        return tableClass;
      }
    }
    return null;
  }

  private javax.lang.model.type.TypeMirror getRepositoryValue(final TypeElement typeElement) {
    try {
      typeElement.getAnnotation(Repository.class).value();
      return null;
    } catch (MirroredTypeException e) {
      return e.getTypeMirror();
    }
  }
}
