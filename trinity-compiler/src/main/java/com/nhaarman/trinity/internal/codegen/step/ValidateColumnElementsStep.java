package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.annotations.Column;
import com.nhaarman.trinity.internal.codegen.ProcessingException;
import com.nhaarman.trinity.internal.codegen.validator.ColumnTypeValidator;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import org.jetbrains.annotations.NotNull;

public class ValidateColumnElementsStep implements ProcessingStep {

  @NotNull
  private final ColumnTypeValidator mColumnTypeValidator;

  public ValidateColumnElementsStep() {
    mColumnTypeValidator = new ColumnTypeValidator();
  }

  @Override
  public void process(@NotNull final RoundEnvironment roundEnvironment) throws ProcessingException {
    Set<? extends Element> columnElements = roundEnvironment.getElementsAnnotatedWith(Column.class);
    mColumnTypeValidator.validate(columnElements);
  }
}
