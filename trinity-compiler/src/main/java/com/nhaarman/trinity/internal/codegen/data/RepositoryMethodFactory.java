package com.nhaarman.trinity.internal.codegen.data;

import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod.Builder;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import org.jetbrains.annotations.NotNull;

public class RepositoryMethodFactory {

  public RepositoryMethod createRepositoryMethod(@NotNull final ExecutableElement element) {

    TypeMirror type = element.getReturnType();
    String name = element.getSimpleName().toString();

    List<Parameter> parameters = new ArrayList<>();
    for (VariableElement variableElement : element.getParameters()) {
      parameters.add(new Parameter(variableElement));
    }

    return new Builder().withName(name).withType(type).withParameters(parameters).withElement(element).build();
  }
}
