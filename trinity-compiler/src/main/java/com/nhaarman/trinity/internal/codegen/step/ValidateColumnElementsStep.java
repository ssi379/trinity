package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.annotations.Column;
import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import com.nhaarman.trinity.internal.codegen.validator.ColumnTypeValidator;
import com.nhaarman.trinity.internal.codegen.validator.ValidationHandler;
import java.io.IOException;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import org.jetbrains.annotations.NotNull;

public class ValidateColumnElementsStep implements ProcessingStep {

  @NotNull
  private final ValidationHandler mValidationHandler;

  @NotNull
  private final ColumnTypeValidator mColumnTypeValidator;

  public ValidateColumnElementsStep(@NotNull final ValidationHandler validationHandler) {
    mValidationHandler = validationHandler;
    mColumnTypeValidator = new ColumnTypeValidator();
  }

  @NotNull
  @Override
  public ProcessingStepResult process(@NotNull final RoundEnvironment roundEnvironment) throws IOException {
    Set<? extends Element> columnElements = roundEnvironment.getElementsAnnotatedWith(Column.class);
    return mColumnTypeValidator.validate(columnElements, mValidationHandler);
  }
}
