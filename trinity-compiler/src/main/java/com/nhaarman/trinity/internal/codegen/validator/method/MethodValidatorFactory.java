package com.nhaarman.trinity.internal.codegen.validator.method;

import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.validator.Validator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MethodValidatorFactory {

  @NotNull
  private final ColumnMethodRepository mColumnMethodRepository;

  @NotNull
  private final RepositoryClass mRepositoryClass;

  public MethodValidatorFactory(@NotNull final ColumnMethodRepository columnMethodRepository, @NotNull final RepositoryClass repositoryClass) {
    mColumnMethodRepository = columnMethodRepository;
    mRepositoryClass = repositoryClass;
  }

  @Nullable
  public Validator<RepositoryMethod> validatorFor(@NotNull final RepositoryMethod method) {
    switch (method.getMethodName()) {
      case "find":
      case "findById":
        return new FindMethodValidator(mColumnMethodRepository, mRepositoryClass);
      case "create":
        return new CreateMethodValidator();
      default:
        return null;
    }
  }
}
