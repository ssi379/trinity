package com.nhaarman.trinity.internal.codegen.data;

import javax.lang.model.element.VariableElement;
import org.jetbrains.annotations.NotNull;

public class Parameter {

  @NotNull
  private final String mType;

  @NotNull
  private final String mName;

  public Parameter(@NotNull final VariableElement variableElement) {
    mType = variableElement.asType().toString();
    mName = variableElement.getSimpleName().toString();
  }

  @NotNull
  public String getType() {
    return mType;
  }

  @NotNull
  public String getName() {
    return mName;
  }
}