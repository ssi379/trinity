package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.internal.codegen.ProcessingException;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import com.nhaarman.trinity.internal.codegen.data.TableClassRepository;
import com.nhaarman.trinity.internal.codegen.validator.ColumnMethodValidator;
import java.util.Collection;
import javax.annotation.processing.RoundEnvironment;
import org.jetbrains.annotations.NotNull;

public class ColumnMethodValidationStep implements ProcessingStep {

  @NotNull
  private final TableClassRepository mTableClassRepository;

  @NotNull
  private final ColumnMethodRepository mColumnMethodRepository;

  @NotNull
  private final ColumnMethodValidator mColumnMethodValidator;

  public ColumnMethodValidationStep(@NotNull final TableClassRepository tableClassRepository,
                                    @NotNull final ColumnMethodRepository columnMethodRepository) {
    mTableClassRepository = tableClassRepository;
    mColumnMethodRepository = columnMethodRepository;
    mColumnMethodValidator = new ColumnMethodValidator();
  }

  @Override
  public void process(@NotNull final RoundEnvironment roundEnvironment) throws ProcessingException {
    Collection<TableClass> tableClasses = mTableClassRepository.all();
    for (TableClass tableClass : tableClasses) {
      Collection<ColumnMethod> getters = mColumnMethodRepository.findGettersForTableClass(tableClass.getFullyQualifiedName());
      Collection<ColumnMethod> setters = mColumnMethodRepository.findSettersForTableClass(tableClass.getFullyQualifiedName());
      mColumnMethodValidator.validate(getters, setters);
    }
  }
}
