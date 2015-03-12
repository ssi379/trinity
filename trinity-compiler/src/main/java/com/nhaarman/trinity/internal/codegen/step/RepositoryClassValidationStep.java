package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.internal.codegen.ProcessingException;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClassRepository;
import com.nhaarman.trinity.internal.codegen.validator.RepositoryClassValidator;
import java.util.Collection;
import javax.annotation.processing.RoundEnvironment;
import org.jetbrains.annotations.NotNull;

public class RepositoryClassValidationStep implements ProcessingStep {

  @NotNull
  private final RepositoryClassRepository mRepositoryClassRepository;

  @NotNull
  private final RepositoryClassValidator mRepositoryClassValidator;

  public RepositoryClassValidationStep(@NotNull final RepositoryClassRepository repositoryClassRepository) {
    mRepositoryClassRepository = repositoryClassRepository;
    mRepositoryClassValidator = new RepositoryClassValidator();
  }

  @Override
  public void process(@NotNull final RoundEnvironment roundEnvironment) throws ProcessingException {
    Collection<RepositoryClass> repositoryClasses = mRepositoryClassRepository.all();
    mRepositoryClassValidator.validate(repositoryClasses);
  }
}
