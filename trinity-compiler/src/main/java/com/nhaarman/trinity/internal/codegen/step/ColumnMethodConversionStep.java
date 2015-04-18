package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.annotations.Column;
import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodFactory;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.ExecutableElement;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.OK;

public class ColumnMethodConversionStep implements ProcessingStep {

  @NotNull
  private final ColumnMethodRepository mColumnMethodRepository;

  @NotNull
  private final ColumnMethodFactory mColumnMethodFactory;

  public ColumnMethodConversionStep(@NotNull final ColumnMethodRepository columnMethodRepository) {
    mColumnMethodRepository = columnMethodRepository;
    mColumnMethodFactory = new ColumnMethodFactory();
  }

  @NotNull
  @Override
  public ProcessingStepResult process(@NotNull final RoundEnvironment roundEnvironment) throws IOException {
    Set<? extends ExecutableElement> columnElements = (Set<? extends ExecutableElement>) roundEnvironment.getElementsAnnotatedWith(Column.class);
    Collection<ColumnMethod> columnMethods = mColumnMethodFactory.createColumnMethods(columnElements);
    mColumnMethodRepository.save(columnMethods);

    return OK;
  }
}
