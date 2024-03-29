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

import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.Parameter;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
class CreateMethodCreator implements MethodCreator {

  @NotNull
  private final TableClass mTableClass;

  @NotNull
  private final MethodSpec mCreateContentValuesSpec;

  @NotNull
  private final RepositoryMethod mMethod;

  @Nullable
  private final ColumnMethod mPrimaryKeySetter;

  CreateMethodCreator(@NotNull final TableClass tableClass,
                      @NotNull final MethodSpec createContentValuesSpec,
                      @NotNull final RepositoryMethod method,
                      @Nullable final ColumnMethod primaryKeySetter) {
    mTableClass = tableClass;
    mCreateContentValuesSpec = createContentValuesSpec;
    mMethod = method;
    mPrimaryKeySetter = primaryKeySetter;
  }

  @NotNull
  @Override
  public MethodSpec create() {
    Parameter parameter = mMethod.getParameter();

    Builder builder = MethodSpec.methodBuilder(mMethod.getMethodName())
        .addJavadoc(createJavadoc())
        .addAnnotation(Override.class)
        .addModifiers(PUBLIC)
        .addParameter(ClassName.bestGuess(parameter.getFullyQualifiedType()), parameter.getVariableName(), FINAL)
        .returns(Long.class)
        .addStatement("$T result = null", Long.class)
        .addCode("\n")
        .addStatement("$T contentValues = $N($L)", CONTENT_VALUES, mCreateContentValuesSpec, parameter.getVariableName())
        .addStatement("$T id = mDatabase.insert($S, null, contentValues)", long.class, mTableClass.getTableName())
        .beginControlFlow("if (id != -1)");

    if (mPrimaryKeySetter != null && Long.class.getName().equals(mPrimaryKeySetter.getType())) {
      builder.addStatement("$L.$L(id)", parameter.getVariableName(), mPrimaryKeySetter.getMethodName());
    }

    builder.addStatement("result = id")
        .endControlFlow()
        .addCode("\n")
        .addStatement("return result");

    return builder.build();
  }

  private String createJavadoc() {
    Parameter parameter = mMethod.getParameter();
    return ""
        + "Executes an insert statement to persist given " + parameter.getFullyQualifiedType() + " in the database.\n"
        + "When successful, the id of the " + parameter.getFullyQualifiedType() + " will be set to the id of the created row.\n"
        + "\n"
        + "@param " + parameter.getVariableName() + " The " + parameter.getFullyQualifiedType() + " to insert.\n"
        + "\n"
        + "@return The created row id, or null if an error occurred.\n";
  }
}
