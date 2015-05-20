package com.nhaarman.trinity.internal.codegen.data;

import com.nhaarman.trinity.internal.codegen.method.CreateMethod;
import com.nhaarman.trinity.internal.codegen.method.FindAllMethod;
import com.nhaarman.trinity.internal.codegen.method.FindMethod;
import com.nhaarman.trinity.internal.codegen.method.RepositoryMethod;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import org.jetbrains.annotations.NotNull;

public class RepositoryMethodFactory {

  public RepositoryMethod createRepositoryMethod(@NotNull final ExecutableElement element) {
    String returnType = element.getReturnType().toString();
    String methodName = element.getSimpleName().toString();

    List<Parameter> parameters = new ArrayList<>();
    for (VariableElement variableElement : element.getParameters()) {
      parameters.add(
          new Parameter(
              variableElement.asType().toString(),
              variableElement.getSimpleName().toString()
          )
      );
    }

    switch (methodName) {
      case "find":
      case "findById":
        return new FindMethod(methodName, returnType, parameters, element);
      case "findAll":
        return new FindAllMethod(methodName, returnType, parameters, element);
      case "create":
        return new CreateMethod(methodName, returnType, parameters, element);
      default:
        throw new IllegalStateException("Unsupported method: " + methodName);
    }
  }
}
