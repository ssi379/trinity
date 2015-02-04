package com.nhaarman.trinity.internal.codegen.data;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

public class RepositoryMethod {

  private final ExecutableElement mExecutableElement;

  private final Collection<Parameter> mParameters = new LinkedHashSet<>();

  public RepositoryMethod(final ExecutableElement executableElement) {
    mExecutableElement = executableElement;

    for (VariableElement variableElement : mExecutableElement.getParameters()) {
      mParameters.add(new Parameter(variableElement));
    }
  }

  public String getMethodName() {
    return mExecutableElement.getSimpleName().toString();
  }

  public String getReturnType() {
    return mExecutableElement.getReturnType().toString();
  }

  public Collection<Parameter> getParameters() {
    return Collections.unmodifiableCollection(mParameters);
  }

  public Parameter getParameter() {
    return mParameters.iterator().next();
  }

  public static class Parameter {

    private final String mType;

    private final String mName;

    public Parameter(final VariableElement variableElement) {
      mType = variableElement.asType().toString();
      mName = variableElement.getSimpleName().toString();
    }

    public String getType() {
      return mType;
    }

    public String getName() {
      return mName;
    }
  }
}
