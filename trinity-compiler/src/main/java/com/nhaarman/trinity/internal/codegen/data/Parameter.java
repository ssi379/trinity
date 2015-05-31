package com.nhaarman.trinity.internal.codegen.data;

import javax.lang.model.type.TypeMirror;
import org.jetbrains.annotations.NotNull;

/**
 * A class which represents a method parameter.
 */
public class Parameter {

  @NotNull
  private final String mFullyQualifiedType;

  @NotNull
  private final String mVariableName;

  @NotNull
  private final TypeMirror mTypeMirror;

  public Parameter(@NotNull final String fullyQualifiedType,
                   @NotNull final String variableName,
                   @NotNull final TypeMirror typeMirror) {
    mFullyQualifiedType = fullyQualifiedType;
    mVariableName = variableName;
    mTypeMirror = typeMirror;
  }

  @NotNull
  public String getFullyQualifiedType() {
    return mFullyQualifiedType;
  }

  @NotNull
  public String getVariableName() {
    return mVariableName;
  }

  @NotNull
  public TypeMirror getType() {
    return mTypeMirror;
  }
}