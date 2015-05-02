package com.nhaarman.trinity.internal.codegen.method;

import com.nhaarman.trinity.internal.codegen.data.Parameter;
import java.util.Collection;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import org.jetbrains.annotations.NotNull;

public class FindAllMethod extends RepositoryMethod {

  public FindAllMethod(@NotNull final String name,
                       @NotNull final TypeMirror type,
                       @NotNull final Collection<Parameter> parameters,
                       @NotNull final Element element) {
    super(name, type, parameters, element);
  }

  @Override
  public void accept(@NotNull final MethodVisitor visitor) {
      visitor.visit(this);
  }
}
