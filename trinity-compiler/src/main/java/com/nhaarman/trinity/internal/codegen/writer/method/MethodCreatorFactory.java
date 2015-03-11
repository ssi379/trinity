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

import com.nhaarman.trinity.internal.codegen.ProcessingException;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;

public class MethodCreatorFactory {

  @NotNull
  private final RepositoryClass mRepositoryClass;

  @NotNull
  private final TableClass mTableClass;

  @NotNull
  private final FieldSpec mDatabaseFieldSpec;

  @NotNull
  private final MethodSpec mReadCursorSpec;

  @NotNull
  private final MethodSpec mCreateContentValuesSpec;

  public MethodCreatorFactory(@NotNull final RepositoryClass repositoryClass,
                              @NotNull final TableClass tableClass,
                              @NotNull final FieldSpec databaseFieldSpec,
                              @NotNull final MethodSpec readCursorSpec,
                              @NotNull final MethodSpec createContentValuesSpec) {
    mRepositoryClass = repositoryClass;
    mTableClass = tableClass;
    mDatabaseFieldSpec = databaseFieldSpec;
    mReadCursorSpec = readCursorSpec;
    mCreateContentValuesSpec = createContentValuesSpec;
  }

  public MethodCreator creatorFor(@NotNull final RepositoryMethod method) throws ProcessingException {
    switch (method.getMethodName().toLowerCase(Locale.ENGLISH)) {
      case "find":
      case "findbyid":
        return new FindMethodCreator(mTableClass, mDatabaseFieldSpec, mReadCursorSpec, method);
      case "create":
        return new CreateMethodCreator(mRepositoryClass, mTableClass, mCreateContentValuesSpec, method);
      default:
        throw new ProcessingException(String.format("Cannot implement %s: unknown method.", method.getMethodName()), method.getElement());
    }
  }
}
