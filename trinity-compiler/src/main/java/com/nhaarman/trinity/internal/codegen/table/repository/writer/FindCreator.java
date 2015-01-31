package com.nhaarman.trinity.internal.codegen.table.repository.writer;

import android.database.Cursor;
import com.nhaarman.trinity.internal.codegen.table.repository.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.table.repository.RepositoryMethod;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import org.jetbrains.annotations.NotNull;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

public class FindCreator implements MethodCreator {

  @NotNull
  private final RepositoryClass mRepositoryClass;

  @NotNull
  private final MethodSpec mReadCursorSpec;

  @NotNull
  private final RepositoryMethod mMethod;

  public FindCreator(@NotNull final RepositoryClass repositoryClass,
                     @NotNull final MethodSpec readCursorSpec,
                     @NotNull final RepositoryMethod method) {
    mRepositoryClass = repositoryClass;
    mReadCursorSpec = readCursorSpec;
    mMethod = method;
  }

  @Override
  public MethodSpec create() {
    return MethodSpec.methodBuilder(mMethod.getMethodName())
        .addAnnotation(Override.class)
        .addModifiers(PUBLIC)
        .addParameter(ClassName.bestGuess(mMethod.getParameter().getType()), mMethod.getParameter().getName(), FINAL)
        .returns(ClassName.bestGuess(mMethod.getReturnType()))
        .beginControlFlow("if (id == null)")
        .addStatement("return null")
        .endControlFlow()
        .addCode("\n")
        .addStatement("$T result = null", ClassName.bestGuess(mRepositoryClass.getTableClass().getEntityFullyQualifiedName()))
        .addCode("\n")
        .addStatement(
            "$T cursor = new $T()" +
                ".from($S)" +
                ".where(\"id=?\", id)" +
                ".limit(\"1\")" +
                ".fetchFrom(mDatabase)",
            Cursor.class,
            ClassName.bestGuess("com.nhaarman.trinity.query.Select"),
            mRepositoryClass.getTableClass().getTableName()
        )
        .beginControlFlow("try")
        .beginControlFlow("if (cursor.moveToFirst())")
        .addStatement("result = $N(cursor)", mReadCursorSpec)
        .endControlFlow()
        .nextControlFlow("finally")
        .addStatement("cursor.close()")
        .endControlFlow()
        .addCode("\n")
        .addStatement("return result")
        .build();
  }
}
