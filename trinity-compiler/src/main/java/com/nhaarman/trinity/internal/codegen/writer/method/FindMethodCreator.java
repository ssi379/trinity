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
import com.nhaarman.trinity.internal.codegen.method.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.AndroidClasses.CURSOR;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

@SuppressWarnings("HardCodedStringLiteral")
class FindMethodCreator implements MethodCreator {

  @NotNull
  private final TableClass mTableClass;

  @NotNull
  private final FieldSpec mDatabaseFieldSpec;

  @NotNull
  private final MethodSpec mReadCursorSpec;

  @NotNull
  private final RepositoryMethod mMethod;

  @NotNull
  private final ColumnMethod mPrimaryKeyMethod;

  FindMethodCreator(@NotNull final TableClass tableClass,
                    @NotNull final FieldSpec databaseFieldSpec,
                    @NotNull final MethodSpec readCursorSpec,
                    @NotNull final RepositoryMethod method,
                    @NotNull final ColumnMethod primaryKeyMethod) {
    mTableClass = tableClass;
    mDatabaseFieldSpec = databaseFieldSpec;
    mReadCursorSpec = readCursorSpec;
    mMethod = method;
    mPrimaryKeyMethod = primaryKeyMethod;
  }

  @NotNull
  @Override
  public MethodSpec create() {
    ClassName entityClassName = ClassName.get(mTableClass.getPackageName(), mTableClass.getClassName());
    String parameterName = mMethod.getParameter().getName();
    String columnName = mPrimaryKeyMethod.getColumnName();

    return MethodSpec.methodBuilder(mMethod.getMethodName())
        .addJavadoc(createJavadoc())
        .addAnnotation(Override.class)
        .addModifiers(PUBLIC)
        .addParameter(ClassName.bestGuess(mMethod.getParameter().getType()), parameterName, FINAL)
        .returns(ClassName.bestGuess(mMethod.getReturnType()))
        .beginControlFlow("if (" + parameterName + " == null)")
        .addStatement("return null")
        .endControlFlow()
        .addCode("\n")
        .addStatement("$T result = null", entityClassName)
        .addCode("\n")
        .addStatement(
            "$T cursor = new $T()" +
                ".from($S)" +
                ".where(\"" + columnName + "=?\", " + parameterName + ')' +
                ".limit(\"1\")" +
                ".queryOn($N)",
            CURSOR,
            ClassName.bestGuess("com.nhaarman.trinity.query.select.Select"),
            mTableClass.getTableName(),
            mDatabaseFieldSpec)
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

  @NonNls
  private String createJavadoc() {
    return ""
        + "Performs a query for a " + mMethod.getReturnType() + " with given id.\n"
        + "If no such instance is found, null is returned.\n"
        + '\n'
        + "@param " + mMethod.getParameter().getName() + " The id of the instance to find."
        + '\n'
        + "@return The " + mMethod.getReturnType() + " with given id, or null if it doesn't exist.";
  }
}
