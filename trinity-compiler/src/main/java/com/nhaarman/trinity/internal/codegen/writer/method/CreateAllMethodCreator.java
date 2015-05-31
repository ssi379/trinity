/*
 * Copyright 2015 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nhaarman.trinity.internal.codegen.writer.method;

import com.nhaarman.trinity.internal.codegen.data.Parameter;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeName;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.AndroidClasses.CONTENT_VALUES;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

/**
 * A MethodCreator that creates a MethodSpec for the create method.
 * <p/>
 * The `Long create(T t)` method takes an entity of a class annotated with the @Table annotation as a parameter, and inserts the entity into the database. It returns a Long value,
 * based on the result of `insert`.
 */
@SuppressWarnings("HardCodedStringLiteral")
class CreateAllMethodCreator implements MethodCreator {

  @NotNull
  private final TableClass mTableClass;

  @NotNull
  private final FieldSpec mDatabaseFieldSpec;

  @NotNull
  private final MethodSpec mCreateContentValuesSpec;

  @NotNull
  private final RepositoryMethod mMethod;

  CreateAllMethodCreator(@NotNull final TableClass tableClass,
                         @NotNull final FieldSpec databaseFieldSpec,
                         @NotNull final MethodSpec createContentValuesSpec,
                         @NotNull final RepositoryMethod method) {
    mTableClass = tableClass;
    mDatabaseFieldSpec = databaseFieldSpec;
    mCreateContentValuesSpec = createContentValuesSpec;
    mMethod = method;
  }

  @NotNull
  @Override
  public MethodSpec create() {
    ClassName entityClassName = ClassName.get(mTableClass.getPackageName(), mTableClass.getClassName());
    Parameter parameter = mMethod.getParameter();
    if (parameter == null) {
      throw new IllegalStateException("Missing parameter.");
    }

    Builder builder = MethodSpec.methodBuilder(mMethod.getMethodName())
        .addJavadoc(createJavadoc())
        .addAnnotation(Override.class)
        .addModifiers(PUBLIC)
        .addParameter(TypeName.get(parameter.getType()), parameter.getVariableName(), FINAL)
        .beginControlFlow("try")
        .addStatement("$N.beginTransaction()", mDatabaseFieldSpec)
        .addCode("\n")
        .beginControlFlow("for ($T entity: $L)", entityClassName, parameter.getVariableName())
        .addStatement("$T contentValues = $N(entity)", CONTENT_VALUES, mCreateContentValuesSpec)
        .addStatement("$N.insert($S, null, contentValues)", mDatabaseFieldSpec, mTableClass.getTableName())
        .endControlFlow()
        .addCode("\n")
        .addStatement("$N.setTransactionSuccessful()", mDatabaseFieldSpec)
        .nextControlFlow("finally")
        .addStatement("$N.endTransaction()", mDatabaseFieldSpec)
        .endControlFlow();

    return builder.build();
  }

  private String createJavadoc() {
    Parameter parameter = mMethod.getParameter();
    return ""
        + "Executes insert statements to persist given " + parameter.getFullyQualifiedType() + " in the database.\n"
        + "\n"
        + "@param " + parameter.getVariableName() + " The entities to insert.\n";
  }
}
