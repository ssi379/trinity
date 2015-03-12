package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.annotations.Repository;
import com.nhaarman.trinity.internal.codegen.ProcessingException;
import com.nhaarman.trinity.internal.codegen.validator.RepositoryTypeValidator;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import org.jetbrains.annotations.NotNull;

public class ValidateRepositoryElementsStep implements ProcessingStep {

  @NotNull
  private final RepositoryTypeValidator mColumnTypeValidator;

  public ValidateRepositoryElementsStep() {
    mColumnTypeValidator = new RepositoryTypeValidator();
  }

  @Override
  public void process(@NotNull final RoundEnvironment roundEnvironment) throws ProcessingException {
    Set<? extends Element> repositoryElements = roundEnvironment.getElementsAnnotatedWith(Repository.class);
    mColumnTypeValidator.validate(repositoryElements);
  }
}
