package com.nhaarman.trinity.internal.codegen.method;

import com.nhaarman.trinity.internal.codegen.data.Parameter;
import java.util.Collection;
import javax.lang.model.element.Element;
import org.jetbrains.annotations.NotNull;

public class CreateMethod extends RepositoryMethod {

  public CreateMethod(@NotNull final String name,
                      @NotNull final String returnType,
                      @NotNull final Collection<Parameter> parameters,
                      @NotNull final Element element) {
    super(name, returnType, parameters, element);
  }

  @Override
  public void accept(@NotNull final MethodVisitor visitor) {
    visitor.visit(this);
  }
}
