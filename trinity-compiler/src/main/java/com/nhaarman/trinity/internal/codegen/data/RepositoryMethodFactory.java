package com.nhaarman.trinity.internal.codegen.data;

import com.nhaarman.trinity.internal.codegen.method.CreateMethod;
import com.nhaarman.trinity.internal.codegen.method.FindMethod;
import com.nhaarman.trinity.internal.codegen.method.RepositoryMethod;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import org.jetbrains.annotations.NotNull;

public class RepositoryMethodFactory {

  public RepositoryMethod createRepositoryMethod(@NotNull final ExecutableElement element) {
    TypeMirror type = element.getReturnType();
    String methodName = element.getSimpleName().toString();

    List<Parameter> parameters = new ArrayList<>();
    for (VariableElement variableElement : element.getParameters()) {
      parameters.add(new Parameter(variableElement));
    }

    switch (methodName) {
      case "find":
      case "findById":
        return new FindMethod(methodName, type, parameters, element);
      case "create":
        return new CreateMethod(methodName, type, parameters, element);
      default:
        throw new IllegalStateException("Unsupported method: " + methodName);
    }
  }
}
