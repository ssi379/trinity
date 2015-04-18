package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.annotations.Table;
import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import com.nhaarman.trinity.internal.codegen.validator.TableTypeValidator;
import com.nhaarman.trinity.internal.codegen.validator.ValidationHandler;
import java.io.IOException;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import org.jetbrains.annotations.NotNull;

public class ValidateTableElementsStep implements ProcessingStep {

  @NotNull
  private final ValidationHandler mValidationHandler;

  @NotNull
  private final TableTypeValidator mTableTypeValidator;

  public ValidateTableElementsStep(@NotNull final ValidationHandler validationHandler) {
    mValidationHandler = validationHandler;
    mTableTypeValidator = new TableTypeValidator();
  }

  @NotNull
  @Override
  public ProcessingStepResult process(@NotNull final RoundEnvironment roundEnvironment) throws IOException {
    Set<? extends Element> tableElements = roundEnvironment.getElementsAnnotatedWith(Table.class);
    return mTableTypeValidator.validate(tableElements, mValidationHandler);
  }
}
