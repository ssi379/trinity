package com.nhaarman.trinity.internal.codegen.validator.method;

import com.nhaarman.trinity.internal.codegen.ValidationException;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.validator.Validator;
import org.jetbrains.annotations.NotNull;

public class MethodValidatorFactory {

  @NotNull
  private final ColumnMethodRepository mColumnMethodRepository;
  @NotNull
  private final RepositoryClass mRepositoryClass;

  public MethodValidatorFactory(@NotNull final ColumnMethodRepository columnMethodRepository, @NotNull final RepositoryClass repositoryClass) {
    mColumnMethodRepository = columnMethodRepository;
    mRepositoryClass = repositoryClass;
  }

  public Validator<RepositoryMethod> validatorFor(@NotNull final RepositoryMethod method) throws ValidationException {
    switch (method.getMethodName()) {
      case "find":
      case "findById":
        return new FindMethodValidator(mColumnMethodRepository, mRepositoryClass);
      default:
        throw new ValidationException(String.format("'%s' is not a supported method name.", method.toString()), method.getElement());
    }
  }
}
