package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.internal.codegen.ProcessingException;
import com.nhaarman.trinity.internal.codegen.data.TableClassRepository;
import com.nhaarman.trinity.internal.codegen.validator.TableClassValidator;
import javax.annotation.processing.RoundEnvironment;
import org.jetbrains.annotations.NotNull;

public class TableClassValidationStep implements ProcessingStep {

  @NotNull
  private final TableClassRepository mTableClassRepository;

  @NotNull
  private final TableClassValidator mTableClassValidator;

  public TableClassValidationStep(@NotNull final TableClassRepository tableClassRepository) {
    mTableClassRepository = tableClassRepository;
    mTableClassValidator = new TableClassValidator();
  }

  @Override
  public void process(@NotNull final RoundEnvironment roundEnvironment) throws ProcessingException {
    mTableClassValidator.validate(mTableClassRepository.all());
  }
}
