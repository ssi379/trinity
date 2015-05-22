package com.nhaarman.trinity.internal.codegen.validator.method;

import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.validator.ValidationHandler;
import com.nhaarman.trinity.internal.codegen.validator.Validator;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.OK;

public class FindAllMethodValidator implements Validator<RepositoryMethod> {

  @NotNull
  @Override
  public ProcessingStepResult validate(@NotNull final RepositoryMethod repositoryMethod, @NotNull final ValidationHandler validationHandler) {
    return OK;
  }
}
