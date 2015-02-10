package com.nhaarman.trinity.internal.codegen.data;

import com.nhaarman.trinity.annotations.PrimaryKey;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ColumnMethod {

  @NotNull
  private final ExecutableElement mExecutableElement;

  @Nullable
  private final PrimaryKeyInfo mPrimaryKeyInfo;

  public ColumnMethod(@NotNull final ExecutableElement executableElement) {
    mExecutableElement = executableElement;
    if (mExecutableElement.getAnnotation(PrimaryKey.class) != null) {
      mPrimaryKeyInfo =
          new PrimaryKeyInfo(mExecutableElement.getAnnotation(PrimaryKey.class).autoIncrement());
    } else {
      mPrimaryKeyInfo = null;
    }
  }

  @NotNull
  public Element getElement() {
    return mExecutableElement;
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
