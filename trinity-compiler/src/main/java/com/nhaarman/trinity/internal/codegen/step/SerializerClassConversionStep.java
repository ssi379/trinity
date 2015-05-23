package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.annotations.Serializer;
import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import com.nhaarman.trinity.internal.codegen.data.SerializerClass;
import com.nhaarman.trinity.internal.codegen.data.SerializerClassFactory;
import com.nhaarman.trinity.internal.codegen.data.SerializerClassRepository;
import java.io.IOException;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.OK;

public class SerializerClassConversionStep implements ProcessingStep {

  @NotNull
  private final SerializerClassRepository mSerializerClassRepository;

  @NotNull
  private final SerializerClassFactory mSerializerClassFactory;

  public SerializerClassConversionStep(@NotNull final SerializerClassRepository serializerClassRepository) {
    mSerializerClassRepository = serializerClassRepository;
    mSerializerClassFactory = new SerializerClassFactory();
  }

  @NotNull
  @Override
  public ProcessingStepResult process(@NotNull final RoundEnvironment roundEnvironment) throws IOException {
    Set<? extends TypeElement> serializerElements = (Set<? extends TypeElement>) roundEnvironment.getElementsAnnotatedWith(Serializer.class);
    Set<SerializerClass> serializerClasses = mSerializerClassFactory.createSerializerClasses(serializerElements);
    mSerializerClassRepository.store(serializerClasses);

    return OK;
  }
}
