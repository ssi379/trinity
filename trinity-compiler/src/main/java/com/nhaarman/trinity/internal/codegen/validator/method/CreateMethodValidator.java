package com.nhaarman.trinity.internal.codegen.validator.method;

import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import com.nhaarman.trinity.internal.codegen.method.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.validator.ValidationHandler;
import com.nhaarman.trinity.internal.codegen.validator.Validator;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.OK;

class CreateMethodValidator implements Validator<RepositoryMethod> {

  @NotNull
  @Override
  public ProcessingStepResult validate(@NotNull final RepositoryMethod method, @NotNull final ValidationHandler validationHandler) {
    return OK;
  }
}
