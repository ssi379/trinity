package com.nhaarman.ellie.internal.codegen.table;

import com.nhaarman.ellie.internal.codegen.column.ColumnInfo;
import com.nhaarman.ellie.internal.codegen.column.ColumnInfoFactory;
import com.nhaarman.lib_setup.annotations.Column;
import com.nhaarman.lib_setup.annotations.Table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

public class TableInfoFactory {

  private Table mAnnotation;

  private TypeElement mElement;

  private RoundEnvironment mRoundEnvironment;

  public Collection<TableInfo> createTableInfos(final Set<? extends Element> tableElements, final RoundEnvironment roundEnvironment) {
    Collection<TableInfo> nodes = new ArrayList<>();

    for (Element tableElement : tableElements) {
      nodes.add(createTableInfo((TypeElement) tableElement, roundEnvironment));
    }

    return nodes;
  }

  public TableInfo createTableInfo(final TypeElement element, final RoundEnvironment roundEnvironment) {
    TableInfo result = new TableInfo();

    mElement = element;
    mRoundEnvironment = roundEnvironment;
    mAnnotation = element.getAnnotation(Table.class);

    result.setElement(element);
    result.setTableName(getTableName());
    result.setPackageName(getPackageName());
    result.setEntityFQN(getEntityFQN());

    result.setColumns(getColumns());

    return result;
  }

  private Map<String, ColumnInfo> getColumns() {
    ColumnInfoFactory columnInfoFactory = new ColumnInfoFactory();

    Set<ExecutableElement> annotatedElements = (Set<ExecutableElement>) mRoundEnvironment.getElementsAnnotatedWith(Column.class);
    for (Iterator<? extends Element> iterator = annotatedElements.iterator(); iterator.hasNext(); ) {
      Element annotatedElement = iterator.next();
      if (!annotatedElement.getEnclosingElement().equals(mElement)) {
        iterator.remove();
      }
    }

    return columnInfoFactory.createColumnInfoList(annotatedElements);
  }

  private String getTableName() {
    return mAnnotation.name();
  }

  private String getPackageName() {
    String entityFQN = mElement.getQualifiedName().toString();
    return entityFQN.substring(0, entityFQN.lastIndexOf('.'));
  }

  private String getEntityFQN() {
    return mElement.getQualifiedName().toString();
  }

}
