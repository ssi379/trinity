package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.SerializerClassRepository;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import com.nhaarman.trinity.internal.codegen.data.TableClassRepository;
import com.nhaarman.trinity.internal.codegen.validator.ColumnMethodValidator;
import com.nhaarman.trinity.internal.codegen.validator.ValidationHandler;
import java.io.IOException;
import java.util.Collection;
import javax.annotation.processing.RoundEnvironment;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.OK;

public class ColumnMethodValidationStep implements ProcessingStep {

  @NotNull
  private final TableClassRepository mTableClassRepository;

  @NotNull
  private final ColumnMethodRepository mColumnMethodRepository;

  @NotNull
  private final ValidationHandler mValidationHandler;

  @NotNull
  private final ColumnMethodValidator mColumnMethodValidator;

  public ColumnMethodValidationStep(@NotNull final TableClassRepository tableClassRepository,
                                    @NotNull final ColumnMethodRepository columnMethodRepository,
                                    @NotNull final SerializerClassRepository serializerClassRepository,
                                    @NotNull final ValidationHandler validationHandler) {
    mTableClassRepository = tableClassRepository;
    mColumnMethodRepository = columnMethodRepository;
    mValidationHandler = validationHandler;
    mColumnMethodValidator = new ColumnMethodValidator(serializerClassRepository);
  }

  @NotNull
  @Override
  public ProcessingStepResult process(@NotNull final RoundEnvironment roundEnvironment) throws IOException {
    ProcessingStepResult result = OK;

    Collection<TableClass> tableClasses = mTableClassRepository.all();
    for (TableClass tableClass : tableClasses) {
      Collection<ColumnMethod> methods = mColumnMethodRepository.findForTableClass(tableClass.getFullyQualifiedName());
      result = result.and(mColumnMethodValidator.validate(methods, mValidationHandler));
    }

    return result;
  }
}
