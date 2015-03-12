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

package com.nhaarman.trinity.internal.codegen.writer.readcursor;

import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.squareup.javapoet.CodeBlock;
import org.jetbrains.annotations.NotNull;

public class StringReadCursorCreator implements ReadCursorCreator {

  /**
   * Resolves to:
   *
   * result.setName(cursor.getString(cursor.getColumnIndex("name")));
   */
  private static final String STATEMENT = "$L.$L($L.getString($L.getColumnIndex($S)))";

  @NotNull
  private final ColumnMethod mColumn;

  @NotNull
  private final String mEntityVariableName;

  @NotNull
  private final String mCursorVariableName;

  public StringReadCursorCreator(@NotNull final ColumnMethod column,
                                 @NotNull final String entityVariableName,
                                 @NotNull final String cursorVariableName) {
    mColumn = column;
    mEntityVariableName = entityVariableName;
    mCursorVariableName = cursorVariableName;
  }

  @Override
  public CodeBlock create() {
    return CodeBlock.builder()
        .addStatement(
            STATEMENT,
            mEntityVariableName,
            mColumn.getMethodName(),
            mCursorVariableName,
            mCursorVariableName,
            mColumn.getMethodName())
        .build();
  }
}
