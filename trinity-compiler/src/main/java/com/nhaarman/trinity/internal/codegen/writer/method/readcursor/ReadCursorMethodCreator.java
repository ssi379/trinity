package com.nhaarman.trinity.internal.codegen.writer.method.readcursor;

import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import com.nhaarman.trinity.internal.codegen.writer.method.MethodCreator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.AndroidClasses.CURSOR;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

public class ReadCursorMethodCreator implements MethodCreator {

  @NotNull
  private final TableClass mTableClass;

  @NotNull
  private final ColumnMethodRepository mColumnMethodRepository;

  public ReadCursorMethodCreator(@NotNull final TableClass tableClass, @NotNull final ColumnMethodRepository columnMethodRepository) {
    mTableClass = tableClass;
    mColumnMethodRepository = columnMethodRepository;
  }

  @NotNull
  @Override
  public MethodSpec create() {
    Collection<ColumnMethod> setters = mColumnMethodRepository.findSettersForTableClass(mTableClass.getFullyQualifiedName());

    ClassName entityClassName = ClassName.get(mTableClass.getPackageName(), mTableClass.getClassName());

    Builder methodBuilder =
        MethodSpec.methodBuilder("read")
            .addModifiers(PUBLIC)
            .addParameter(CURSOR, "cursor", FINAL)
            .returns(entityClassName)
            .addStatement(
                "$T result = new $T()",
                entityClassName,
                entityClassName
            )
            .addCode("\n");

    ReadCursorCreatorFactory creatorFactory = new ReadCursorCreatorFactory("result", "cursor");
    for (ColumnMethod setter : setters) {
      ReadCursorCreator readCursorCreator = creatorFactory.createReadCursorCreator(setter);
      methodBuilder.addCode(readCursorCreator.create());
    }

    return methodBuilder.addCode("\n")
        .addStatement("return result")
        .build();
  }
}
