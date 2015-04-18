package com.nhaarman.trinity.internal.codegen.validator.method;

import com.nhaarman.trinity.internal.codegen.Message;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.validator.ValidationHandler;
import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import com.nhaarman.trinity.internal.codegen.validator.Validator;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.ERROR;
import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.OK;

public class FindMethodValidator implements Validator<RepositoryMethod> {

  @NotNull
  private final ColumnMethodRepository mColumnMethodRepository;

  @NotNull
  private final RepositoryClass mRepositoryClass;

  public FindMethodValidator(@NotNull final ColumnMethodRepository columnMethodRepository,
                             @NotNull final RepositoryClass repositoryClass) {
    mColumnMethodRepository = columnMethodRepository;
    mRepositoryClass = repositoryClass;
  }

  @NotNull
  @Override
  public ProcessingStepResult validate(@NotNull final RepositoryMethod method, @NotNull final ValidationHandler validationHandler) {
    ProcessingStepResult result = OK;

    ColumnMethod primaryKeyGetter = mColumnMethodRepository.findPrimaryKeyGetter(mRepositoryClass.getTableClassFullyQualifiedName());
    ColumnMethod primaryKeySetter = mColumnMethodRepository.findPrimaryKeySetter(mRepositoryClass.getTableClassFullyQualifiedName());

    if (primaryKeyGetter == null && primaryKeySetter == null) {
      validationHandler.onError(
          method.getElement(),
          null,
          Message.MISSING_PRIMARYKEY_METHOD_FOR_FIND_IMPLEMENTATION,
          method.getMethodName(),
          mRepositoryClass.getTableClassFullyQualifiedName()
      );
      result = ERROR;
    }

    if (primaryKeyGetter != null && !primaryKeyGetter.getType().equals(method.getParameter().getType())) {
      validationHandler.onError(
          method.getElement(),
          null,
          Message.PRIMARYKEY_FIND_TYPE_MISMATCH,
          method.getParameter().getType(),
          method.getMethodName(),
          primaryKeyGetter.getType(),
          primaryKeyGetter.getFullyQualifiedTableClassName() + '.' + primaryKeyGetter.getMethodName()
      );
      result = ERROR;
    }

    if (primaryKeySetter != null && !primaryKeySetter.getType().equals(method.getParameter().getType())) {
      validationHandler.onError(
          method.getElement(),
          null,
          Message.PRIMARYKEY_FIND_TYPE_MISMATCH,
          method.getParameter().getType(),
          method.getMethodName(),
          primaryKeySetter.getType(),
          primaryKeySetter.getFullyQualifiedTableClassName() + '.' + primaryKeySetter.getMethodName()
      );
      result = ERROR;
    }

    return result;
  }
}
