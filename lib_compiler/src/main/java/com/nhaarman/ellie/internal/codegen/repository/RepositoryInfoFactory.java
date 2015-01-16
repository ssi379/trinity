package com.nhaarman.ellie.internal.codegen.repository;

import com.nhaarman.ellie.internal.codegen.table.TableInfo;
import com.nhaarman.lib_setup.annotations.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public class RepositoryInfoFactory {

  public Collection<RepositoryInfo> createRepositoryInfo(final Set<? extends Element> repositoryElements,
                                                         final Collection<TableInfo> tableInfos,
                                                         final RoundEnvironment roundEnvironment) {
    Set<RepositoryInfo> results = new HashSet<>();

    for (Element repositoryElement : repositoryElements) {
      TypeElement typeElement = (TypeElement) repositoryElement;
      RepositoryInfo repositoryInfo = new RepositoryInfo(typeElement);
      repositoryInfo.setInterface(typeElement.getKind() == ElementKind.INTERFACE);

      for (TableInfo tableInfo : tableInfos) {
        if (tableInfo.getTableName().equals(typeElement.getAnnotation(Repository.class).tableName())) {
          repositoryInfo.setTableInfo(tableInfo);
        }
      }

      List<? extends Element> enclosedElements = typeElement.getEnclosedElements();
      for (Element enclosedElement : enclosedElements) {
        if (enclosedElement instanceof ExecutableElement) {
          ExecutableElement executableElement = (ExecutableElement) enclosedElement;
          if (executableElement.getModifiers().contains(Modifier.ABSTRACT)) {
            repositoryInfo.addMethodToImplement(executableElement);
          }
        }
      }
      results.add(repositoryInfo);
    }

    return results;
  }

}
