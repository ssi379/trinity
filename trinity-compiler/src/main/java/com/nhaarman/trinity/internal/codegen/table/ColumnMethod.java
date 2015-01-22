package com.nhaarman.trinity.internal.codegen.table;

import com.nhaarman.trinity.annotations.PrimaryKey;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public class ColumnMethod {

  private final ExecutableElement mExecutableElement;

  private final PrimaryKeyInfo mPrimaryKeyInfo;

  public ColumnMethod(final ExecutableElement executableElement) {
    mExecutableElement = executableElement;
    if (mExecutableElement.getAnnotation(PrimaryKey.class) != null) {
      mPrimaryKeyInfo = new PrimaryKeyInfo(mExecutableElement.getAnnotation(PrimaryKey.class).autoIncrement());
    } else {
      mPrimaryKeyInfo = null;
    }
  }

  public boolean isGetter() {
    return mExecutableElement.getReturnType().getKind() != TypeKind.VOID;
  }

  public String getName() {
    return mExecutableElement.getSimpleName().toString();
  }

  public boolean isSetter() {
    return mExecutableElement.getReturnType().getKind() == TypeKind.VOID;
  }

  public TypeMirror getType() {
    if (isGetter()) {
      return mExecutableElement.getReturnType();
    } else {
      return mExecutableElement.getParameters().iterator().next().asType();
    }
  }

  public boolean isPrimary() {
    return mPrimaryKeyInfo != null;
  }
}
