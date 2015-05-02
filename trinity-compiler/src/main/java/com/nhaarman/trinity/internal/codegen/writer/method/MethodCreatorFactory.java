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
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import com.nhaarman.trinity.internal.codegen.method.FindAllMethod;
import com.nhaarman.trinity.internal.codegen.method.RepositoryMethod;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MethodCreatorFactory {

  @NotNull
  private final TableClass mTableClass;

  @NotNull
  private final FieldSpec mDatabaseFieldSpec;

  @NotNull
  private final MethodSpec mReadCursorSpec;

  @NotNull
  private final MethodSpec mCreateContentValuesSpec;

  @Nullable
  private final ColumnMethod mPrimaryKeySetter;

  @Nullable
  private final ColumnMethod mPrimaryKeyGetter;

  public MethodCreatorFactory(@NotNull final TableClass tableClass,
                              @NotNull final FieldSpec databaseFieldSpec,
                              @NotNull final MethodSpec readCursorSpec,
                              @NotNull final MethodSpec createContentValuesSpec,
                              @Nullable final ColumnMethod primaryKeySetter,
                              @Nullable final ColumnMethod primaryKeyGetter) {
    mTableClass = tableClass;
    mDatabaseFieldSpec = databaseFieldSpec;
    mReadCursorSpec = readCursorSpec;
    mCreateContentValuesSpec = createContentValuesSpec;
    mPrimaryKeySetter = primaryKeySetter;
    mPrimaryKeyGetter = primaryKeyGetter;
  }

  @NotNull
  public MethodCreator findMethodCreator(@NotNull final RepositoryMethod method) {
    if (mPrimaryKeyGetter == null && mPrimaryKeySetter == null) {
      throw new IllegalStateException("Missing primary key method.");
    }
    return new FindMethodCreator(mTableClass, mDatabaseFieldSpec, mReadCursorSpec, method, mPrimaryKeySetter == null ? mPrimaryKeyGetter : mPrimaryKeySetter);
  }

  @NotNull
  public MethodCreator createMethodCreator(@NotNull final RepositoryMethod method) {
    return new CreateMethodCreator(mTableClass, mCreateContentValuesSpec, method, mPrimaryKeySetter);
  }

  @NotNull
  public MethodCreator findAllMethodCreator(@NotNull final FindAllMethod method) {
    return new FindAllMethodCreator(mTableClass, mDatabaseFieldSpec, mReadCursorSpec, method);
  }
}
