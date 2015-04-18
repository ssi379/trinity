package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import com.nhaarman.trinity.internal.codegen.data.TableClassRepository;
import com.nhaarman.trinity.internal.codegen.validator.TableClassValidator;
import com.nhaarman.trinity.internal.codegen.validator.ValidationHandler;
import java.io.IOException;
import javax.annotation.processing.RoundEnvironment;
import org.jetbrains.annotations.NotNull;

public class TableClassValidationStep implements ProcessingStep {

  @NotNull
  private final TableClassRepository mTableClassRepository;

  @NotNull
  private final TableClassValidator mTableClassValidator;

  @NotNull
  private final ValidationHandler mValidationHandler;

  public TableClassValidationStep(@NotNull final TableClassRepository tableClassRepository,
                                  @NotNull final ValidationHandler validationHandler) {
    mTableClassRepository = tableClassRepository;
    mValidationHandler = validationHandler;

    mTableClassValidator = new TableClassValidator();
  }

  @NotNull
  @Override
  public ProcessingStepResult process(@NotNull final RoundEnvironment roundEnvironment) throws IOException {
    return mTableClassValidator.validate(mTableClassRepository.all(), mValidationHandler);
  }
}
