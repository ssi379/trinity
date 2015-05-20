package com.nhaarman.trinity.internal.codegen.validator.method;

import com.nhaarman.trinity.internal.codegen.Message;
import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import com.nhaarman.trinity.internal.codegen.data.Parameter;
import com.nhaarman.trinity.internal.codegen.method.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.validator.ValidationHandler;
import com.nhaarman.trinity.internal.codegen.validator.Validator;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.ERROR;
import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.OK;

class CreateMethodValidator implements Validator<RepositoryMethod> {

  @NotNull
  @Override
  public ProcessingStepResult validate(@NotNull final RepositoryMethod method, @NotNull final ValidationHandler validationHandler) {
    String returnType = method.getReturnType();
    if (!Long.class.getName().equals(returnType)) {
      validationHandler.onError(method.getElement(), null, Message.CREATE_METHOD_MUST_RETURN_LONG);
      return ERROR;
    }

    Collection<Parameter> parameters = method.getParameters();
    if (parameters.size() != 1) {
      validationHandler.onError(method.getElement(), null, Message.CREATE_METHOD_MUST_RECEIVE_EXACTLY_ONE_PARAMETER);
      return ERROR;
    }

    return OK;
  }
}
