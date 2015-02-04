package com.nhaarman.trinity.internal.codegen.writer.method;

import android.content.ContentValues;
import com.nhaarman.trinity.internal.codegen.data.Column;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod.Parameter;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import org.jetbrains.annotations.NotNull;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

/**
 * A MethodCreator that creates a MethodSpec for the create method.
 *
 * The `Long create(T t)` method takes an entity of a class annotated with the @Table annotation as a parameter,
 * and inserts the entity into the database. It returns a Long value, based on the result of `insert`.
 */
class CreateMethodCreator implements MethodCreator {

  @NotNull
  private final RepositoryClass mRepositoryClass;
  @NotNull
  private final MethodSpec mCreateContentValuesSpec;
  @NotNull
  private final RepositoryMethod mMethod;

  CreateMethodCreator(@NotNull final RepositoryClass repositoryClass,
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
        .addJavadoc(createJavadoc())
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

  private String createJavadoc() {
    Parameter parameter = mMethod.getParameter();
    return ""
        + "Executes an insert statement to persist given " + parameter.getType() + " in the database.\n"
        + "When successful, the id of the " + parameter.getType() + " will be set to the id of the created row.\n"
        + "\n"
        + "@param " + parameter.getName() + " The " + parameter.getType() + " to insert.\n"
        + "\n"
        + "@return The created row id, or null if an error occurred.\n";
  }
}
