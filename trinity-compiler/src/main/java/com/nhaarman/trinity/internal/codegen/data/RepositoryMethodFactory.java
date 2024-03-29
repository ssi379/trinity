package com.nhaarman.trinity.internal.codegen.data;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import org.jetbrains.annotations.NotNull;

/**
 * A factory class for building {@link RepositoryMethod} instances out of {@link ExecutableElement}s.
 */
public class RepositoryMethodFactory {

  @NotNull
  public RepositoryMethod createRepositoryMethod(@NotNull final ExecutableElement element) {
    String returnType = element.getReturnType().toString();
    String methodName = element.getSimpleName().toString();

    List<Parameter> parameters = new ArrayList<>();
    for (VariableElement variableElement : element.getParameters()) {
      parameters.add(
          new Parameter(
              variableElement.asType().toString(),
              variableElement.getSimpleName().toString(),
              variableElement.asType()
          )
      );
    }

    return new RepositoryMethod(methodName, returnType, parameters, element);
  }
}
