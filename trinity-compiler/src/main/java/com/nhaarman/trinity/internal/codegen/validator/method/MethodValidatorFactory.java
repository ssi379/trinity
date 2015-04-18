package com.nhaarman.trinity.internal.codegen.validator.method;

import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.method.RepositoryMethod;
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

  @NotNull
  public Validator<RepositoryMethod> findMethodValidator() {
    return new FindMethodValidator(mColumnMethodRepository, mRepositoryClass);
  }

  @NotNull
  public Validator<RepositoryMethod> createMethodValidator() {
    return new CreateMethodValidator();
  }
}
