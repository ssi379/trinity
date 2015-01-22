package com.nhaarman.trinity.internal.codegen.table;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class TableInfoFactory {


  public Collection<TableClass> createTableInfos(final Set<? extends Element> tableElements) {
    Collection<TableClass> tableClasses = new HashSet<>(tableElements.size());

    for (Element tableElement : tableElements) {
      tableClasses.add(new TableClass((TypeElement) tableElement));
    }

    return tableClasses;
  }

}
