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

import com.nhaarman.trinity.internal.codegen.data.Column;
import com.squareup.javapoet.CodeBlock;
import org.jetbrains.annotations.NotNull;

public class LongReadCursorCreator implements ReadCursorCreator {

  /**
   * Resolves to:
   *
   * result.setId(cursor.getLong(cursor.getColumnIndex("id")));
   */
  private static final String STATEMENT = "$L.$L($L.getLong($L.getColumnIndex($S)))";

  @NotNull
  private final Column mColumn;

  @NotNull
  private final String mEntityVariableName;

  @NotNull
  private final String mColumnVariableName;

  public LongReadCursorCreator(@NotNull final Column column,
                               @NotNull final String entityVariableName,
                               @NotNull final String columnVariableName) {
    mColumn = column;
    mEntityVariableName = entityVariableName;
    mColumnVariableName = columnVariableName;
  }

  @Override
  public CodeBlock create() {
    return CodeBlock.builder()
        .addStatement(
            STATEMENT,
            mEntityVariableName,
            mColumn.setter().getName(),
            mColumnVariableName,
            mColumnVariableName,
            mColumn.getName())
        .build();
  }
}
