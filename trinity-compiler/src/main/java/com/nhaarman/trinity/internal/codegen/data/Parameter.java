package com.nhaarman.trinity.internal.codegen.data;

import org.jetbrains.annotations.NotNull;

/**
 * A class which represents a method parameter.
 */
public class Parameter {

  @NotNull
  private final String mFullyQualifiedType;

  @NotNull
  private final String mVariableName;

  public Parameter(@NotNull final String fullyQualifiedType, @NotNull final String variableName) {
    mFullyQualifiedType = fullyQualifiedType;
    mVariableName = variableName;
  }

  @NotNull
  public String getFullyQualifiedType() {
    return mFullyQualifiedType;
  }

  @NotNull
  public String getVariableName() {
    return mVariableName;
  }
}