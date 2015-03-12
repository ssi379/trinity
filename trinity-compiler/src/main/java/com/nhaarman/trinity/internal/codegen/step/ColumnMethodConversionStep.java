package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.annotations.Column;
import com.nhaarman.trinity.internal.codegen.ProcessingException;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodFactory;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import java.util.Collection;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.ExecutableElement;
import org.jetbrains.annotations.NotNull;

public class ColumnMethodConversionStep implements ProcessingStep {

  @NotNull
  private final ColumnMethodRepository mColumnMethodRepository;

  @NotNull
  private final ColumnMethodFactory mColumnMethodFactory;

  public ColumnMethodConversionStep(@NotNull final ColumnMethodRepository columnMethodRepository) {
    mColumnMethodRepository = columnMethodRepository;
    mColumnMethodFactory = new ColumnMethodFactory();
  }

  @Override
  public void process(@NotNull final RoundEnvironment roundEnvironment) throws ProcessingException {
    Set<? extends ExecutableElement> columnElements = (Set<? extends ExecutableElement>) roundEnvironment.getElementsAnnotatedWith(Column.class);
    Collection<ColumnMethod> columnMethods = mColumnMethodFactory.createColumnMethods(columnElements);
    mColumnMethodRepository.save(columnMethods);
  }
}
