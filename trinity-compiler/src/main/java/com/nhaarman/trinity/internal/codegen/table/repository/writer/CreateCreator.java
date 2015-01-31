package com.nhaarman.trinity.internal.codegen.table.repository.writer;

import android.content.ContentValues;
import com.nhaarman.trinity.internal.codegen.table.Column;
import com.nhaarman.trinity.internal.codegen.table.repository.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.table.repository.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.table.repository.RepositoryMethod.Parameter;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import org.jetbrains.annotations.NotNull;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

public class CreateCreator implements MethodCreator {

  @NotNull
  private final RepositoryClass mRepositoryClass;
  @NotNull
  private final MethodSpec mCreateContentValuesSpec;
  @NotNull
  private final RepositoryMethod mMethod;

  public CreateCreator(@NotNull final RepositoryClass repositoryClass,
                       @NotNull final MethodSpec createContentValuesSpec,
                       @NotNull final RepositoryMethod method) {

    mRepositoryClass = repositoryClass;
    mCreateContentValuesSpec = createContentValuesSpec;
    mMethod = method;
  }

  @Override
  public MethodSpec create() {
    Parameter parameter = mMethod.getParameter();
    Column primaryKeyColumn = mRepositoryClass.getTableClass().getPrimaryKeyColumn();

    return MethodSpec.methodBuilder(mMethod.getMethodName())
        .addAnnotation(Override.class)
        .addModifiers(PUBLIC)
        .addParameter(ClassName.bestGuess(parameter.getType()), parameter.getName(), FINAL)
        .returns(ClassName.bestGuess(mMethod.getReturnType()))
        .addStatement("$T result = null", Long.class)
        .addCode("\n")
        .addStatement("$T contentValues = $N($L)", ContentValues.class, mCreateContentValuesSpec, parameter.getName())
        .addStatement("$T id = mDatabase.insert($S, null, contentValues)", long.class, mRepositoryClass.getTableClass().getTableName())
        .beginControlFlow("if (id != -1)")
        .addStatement("$L.$L(id)", parameter.getName(), primaryKeyColumn.setter().getName())
        .addStatement("result = id")
        .endControlFlow()
        .addCode("\n")
        .addStatement("return result")
        .build();
  }
}
