package com.nhaarman.trinity.internal.codegen.validator;

import com.nhaarman.trinity.internal.codegen.ProcessingException;
import java.util.Set;
import javax.lang.model.element.Element;
import org.jetbrains.annotations.NotNull;

public class ColumnTypeValidator implements Validator<Set<? extends Element>> {

  @Override
  public void validate(@NotNull final Set<? extends Element> columnElements) throws ProcessingException {
  }
}
