package com.nhaarman.ellie.internal.codegen.repository;

import com.nhaarman.ellie.internal.codegen.table.TableInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

public class RepositoryInfo {

  private final TypeElement mRepositoryElement;

  private boolean mInterface;

  private Collection<ExecutableElement> mMethodsToImplement = new HashSet<>();

  private TableInfo mTableInfo;

  public RepositoryInfo(final TypeElement repositoryElement) {
    mRepositoryElement = repositoryElement;
  }

  public Element getRepositoryElement() {
    return mRepositoryElement;
  }

  public boolean isInterface() {
    return mInterface;
  }

  public void setInterface(final boolean anInterface) {
    mInterface = anInterface;
  }

  public void addMethodToImplement(final ExecutableElement executableElement) {
    mMethodsToImplement.add(executableElement);
  }

  public Collection<ExecutableElement> getMethodsToImplement() {
    return Collections.unmodifiableCollection(mMethodsToImplement);
  }

  public String getPackageName() {
    return mRepositoryElement.getQualifiedName().toString().substring(0, mRepositoryElement.getQualifiedName().toString().lastIndexOf('.'));
  }

  public String getFullyQualifiedName() {
    return mRepositoryElement.getQualifiedName().toString();
  }

  public TableInfo getTableInfo() {
    return mTableInfo;
  }

  public void setTableInfo(final TableInfo tableInfo) {
    mTableInfo = tableInfo;
  }
}
