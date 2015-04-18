package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.annotations.Repository;
import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import com.nhaarman.trinity.internal.codegen.validator.RepositoryTypeValidator;
import com.nhaarman.trinity.internal.codegen.validator.ValidationHandler;
import java.io.IOException;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import org.jetbrains.annotations.NotNull;

public class ValidateRepositoryElementsStep implements ProcessingStep {

  @NotNull
  private final ValidationHandler mValidationHandler;

  @NotNull
  private final RepositoryTypeValidator mColumnTypeValidator;

  public ValidateRepositoryElementsStep(@NotNull final ValidationHandler validationHandler) {
    mValidationHandler = validationHandler;
    mColumnTypeValidator = new RepositoryTypeValidator();
  }

  @NotNull
  @Override
  public ProcessingStepResult process(@NotNull final RoundEnvironment roundEnvironment) throws IOException {
    Set<? extends Element> repositoryElements = roundEnvironment.getElementsAnnotatedWith(Repository.class);
    return mColumnTypeValidator.validate(repositoryElements, mValidationHandler);
  }
}
