package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClassRepository;
import com.nhaarman.trinity.internal.codegen.validator.RepositoryClassValidator;
import com.nhaarman.trinity.internal.codegen.validator.ValidationHandler;
import java.io.IOException;
import java.util.Collection;
import javax.annotation.processing.RoundEnvironment;
import org.jetbrains.annotations.NotNull;

public class RepositoryClassValidationStep implements ProcessingStep {

  @NotNull
  private final ValidationHandler mValidationHandler;

  @NotNull
  private final RepositoryClassRepository mRepositoryClassRepository;

  @NotNull
  private final RepositoryClassValidator mRepositoryClassValidator;

  public RepositoryClassValidationStep(@NotNull final RepositoryClassRepository repositoryClassRepository,
                                       @NotNull final ColumnMethodRepository columnMethodRepository,
                                       @NotNull final ValidationHandler validationHandler) {
    mValidationHandler = validationHandler;
    mRepositoryClassRepository = repositoryClassRepository;

    mRepositoryClassValidator = new RepositoryClassValidator(columnMethodRepository);
  }

  @NotNull
  @Override
  public ProcessingStepResult process(@NotNull final RoundEnvironment roundEnvironment) throws IOException {
    Collection<RepositoryClass> repositoryClasses = mRepositoryClassRepository.all();
    return mRepositoryClassValidator.validate(repositoryClasses, mValidationHandler);
  }
}
