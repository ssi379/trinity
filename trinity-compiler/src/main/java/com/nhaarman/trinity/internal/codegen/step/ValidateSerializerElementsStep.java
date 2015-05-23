package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.annotations.Serializer;
import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import com.nhaarman.trinity.internal.codegen.validator.SerializerTypeValidator;
import com.nhaarman.trinity.internal.codegen.validator.ValidationHandler;
import java.io.IOException;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import org.jetbrains.annotations.NotNull;

public class ValidateSerializerElementsStep implements ProcessingStep {

  @NotNull
  private final ValidationHandler mValidationHandler;

  @NotNull
  private final SerializerTypeValidator mSerializerTypeValidator;

  public ValidateSerializerElementsStep(@NotNull final ValidationHandler validationHandler) {
    mValidationHandler = validationHandler;
    mSerializerTypeValidator = new SerializerTypeValidator();
  }

  @NotNull
  @Override
  public ProcessingStepResult process(@NotNull final RoundEnvironment roundEnvironment) throws IOException {
    Set<? extends Element> serializerElements = roundEnvironment.getElementsAnnotatedWith(Serializer.class);
    return mSerializerTypeValidator.validate(serializerElements, mValidationHandler);
  }
}
