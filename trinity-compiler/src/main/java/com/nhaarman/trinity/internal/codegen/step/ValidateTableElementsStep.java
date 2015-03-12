package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.annotations.Table;
import com.nhaarman.trinity.internal.codegen.ProcessingException;
import com.nhaarman.trinity.internal.codegen.validator.TableTypeValidator;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import org.jetbrains.annotations.NotNull;

public class ValidateTableElementsStep implements ProcessingStep {

  @NotNull
  private final TableTypeValidator mTableTypeValidator;

  public ValidateTableElementsStep() {
    mTableTypeValidator = new TableTypeValidator();
  }

  @Override
  public void process(@NotNull final RoundEnvironment roundEnvironment) throws ProcessingException {
    Set<? extends Element> tableElements = roundEnvironment.getElementsAnnotatedWith(Table.class);
    mTableTypeValidator.validate(tableElements);
  }
}
