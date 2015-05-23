package com.nhaarman.trinity.internal.codegen.writer.method;

import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.SerializerClass;
import com.nhaarman.trinity.internal.codegen.data.SerializerClassRepository;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.AndroidClasses.CONTENT_VALUES;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

@SuppressWarnings("HardCodedStringLiteral")
public class CreateContentValuesMethodCreator implements MethodCreator {

  @NotNull
  private final TableClass mTableClass;

  @NotNull
  private final ColumnMethodRepository mColumnMethodRepository;

  @NotNull
  private final SerializerClassRepository mSerializerClassRepository;

  public CreateContentValuesMethodCreator(@NotNull final TableClass tableClass,
                                          @NotNull final ColumnMethodRepository columnMethodRepository,
                                          @NotNull final SerializerClassRepository serializerClassRepository) {
    mTableClass = tableClass;
    mColumnMethodRepository = columnMethodRepository;
    mSerializerClassRepository = serializerClassRepository;
  }

  @NotNull
  @Override
  public MethodSpec create() {
    Collection<ColumnMethod> getters = mColumnMethodRepository.findGettersForTableClass(mTableClass.getFullyQualifiedName());

    ClassName entityClassName = ClassName.get(mTableClass.getPackageName(), mTableClass.getClassName());

    MethodSpec.Builder methodBuilder =
        MethodSpec.methodBuilder("createContentValues")
            .addModifiers(PUBLIC)
            .addParameter(entityClassName, "entity", FINAL)
            .returns(CONTENT_VALUES)
            .addStatement("$T result = new $T()", CONTENT_VALUES, CONTENT_VALUES)
            .addCode("\n");

    for (ColumnMethod getter : getters) {
      SerializerClass serializerClass = mSerializerClassRepository.findByFullyQualifiedSerializableTypeName(getter.getType());
      if (serializerClass == null) {
        methodBuilder.addStatement("result.put($S, entity.$L())", getter.getColumnName(), getter.getMethodName());
      } else {
        methodBuilder.addStatement("result.put($S, new $T().serialize(entity.$L()))", getter.getColumnName(), ClassName.bestGuess(serializerClass.getFullyQualifiedClassName()), getter.getMethodName());
      }
    }

    methodBuilder.addCode("\n");
    methodBuilder.addStatement("return result");

    return methodBuilder.build();
  }
}
