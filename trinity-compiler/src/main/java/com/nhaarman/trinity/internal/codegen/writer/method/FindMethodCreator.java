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

import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.AndroidClasses.CURSOR;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

class FindMethodCreator implements MethodCreator {

  @NotNull
  private final RepositoryClass mRepositoryClass;

  @NotNull
  private final FieldSpec mDatabaseFieldSpec;

  @NotNull
  private final MethodSpec mReadCursorSpec;

  @NotNull
  private final RepositoryMethod mMethod;

  FindMethodCreator(@NotNull final RepositoryClass repositoryClass,
                    @NotNull final FieldSpec databaseFieldSpec,
                    @NotNull final MethodSpec readCursorSpec,
                    @NotNull final RepositoryMethod method) {
    mRepositoryClass = repositoryClass;
    mDatabaseFieldSpec = databaseFieldSpec;
    mReadCursorSpec = readCursorSpec;
    mMethod = method;
  }

  @Override
  public MethodSpec create() {
    return MethodSpec.methodBuilder(mMethod.getMethodName())
        .addJavadoc(createJavadoc())
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
                ".queryOn($N)",
            CURSOR,
            ClassName.bestGuess("com.nhaarman.trinity.query.select.Select"),
            mRepositoryClass.getTableClass().getTableName(),
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

  private String createJavadoc() {
    return ""
        + "Performs a query for a " + mMethod.getReturnType() + " with given id.\n"
        + "If no such instance is found, null is returned.\n"
        + "\n"
        + "@param " + mMethod.getParameter().getName() + " The id of the instance to find."
        + "\n"
        + "@return The " + mMethod.getReturnType() + " with given id, or null if it doesn't exist.";
  }
}
