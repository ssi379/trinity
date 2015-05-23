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

package com.nhaarman.trinity.internal.codegen.writer.method.readcursor;

import com.nhaarman.trinity.internal.codegen.SupportedColumnType;
import com.nhaarman.trinity.internal.codegen.SupportedColumnType.SupportedColumnTypeVisitor;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.SerializerClass;
import com.nhaarman.trinity.internal.codegen.data.SerializerClassRepository;
import org.jetbrains.annotations.NotNull;

public class ReadCursorCreatorFactory {

  /**
   * The name of the variable that represents the result in which values should stored.
   */
  @NotNull
  private final String mResultVariableName;

  /**
   * The name of the Cursor variable to retrieve column values from.
   */
  @NotNull
  private final String mCursorVariableName;

  @NotNull
  private final SerializerClassRepository mSerializerClassRepository;

  public ReadCursorCreatorFactory(@NotNull final String resultVariableName,
                                  @NotNull final String cursorVariableName,
                                  @NotNull final SerializerClassRepository serializerClassRepository) {
    mResultVariableName = resultVariableName;
    mCursorVariableName = cursorVariableName;
    mSerializerClassRepository = serializerClassRepository;
  }

  @NotNull
  public ReadCursorCreator createReadCursorCreator(@NotNull final ColumnMethod column) {
    String typeString = column.getType();

    SerializerClass serializerClass = mSerializerClassRepository.findByFullyQualifiedSerializableTypeName(typeString);
    if (serializerClass != null) {
      return new SerializedReadCursorCreator(column, mResultVariableName, mCursorVariableName, serializerClass);
    }

    SupportedColumnType type = SupportedColumnType.from(typeString);
    if (type != null) {
      SupportedColumnTypeVisitor<ReadCursorCreator> supportedColumnTypeVisitor = new MySupportedColumnTypeVisitor(column);
      return type.accept(supportedColumnTypeVisitor);
    }

    throw new IllegalArgumentException(String.format("Type '%s' is not supported.", typeString));
  }

  private class MySupportedColumnTypeVisitor implements SupportedColumnTypeVisitor<ReadCursorCreator> {

    @NotNull
    private final ColumnMethod mColumn;

    MySupportedColumnTypeVisitor(@NotNull final ColumnMethod column) {
      mColumn = column;
    }

    @NotNull
    @Override
    public ReadCursorCreator acceptInt() {
      return new IntegerReadCursorCreator(mColumn, mResultVariableName, mCursorVariableName);
    }

    @NotNull
    @Override
    public ReadCursorCreator acceptJavaLangInteger() {
      return new IntegerReadCursorCreator(mColumn, mResultVariableName, mCursorVariableName);
    }

    @NotNull
    @Override
    public ReadCursorCreator acceptLong() {
      return new LongReadCursorCreator(mColumn, mResultVariableName, mCursorVariableName);
    }

    @NotNull
    @Override
    public ReadCursorCreator acceptJavaLangLong() {
      return new LongReadCursorCreator(mColumn, mResultVariableName, mCursorVariableName);
    }

    @NotNull
    @Override
    public ReadCursorCreator acceptBoolean() {
      return new BooleanReadCursorCreator(mColumn, mResultVariableName, mCursorVariableName);
    }

    @NotNull
    @Override
    public ReadCursorCreator acceptJavaLangBoolean() {
      return new BooleanReadCursorCreator(mColumn, mResultVariableName, mCursorVariableName);
    }

    @NotNull
    @Override
    public ReadCursorCreator acceptJavaLangString() {
      return new StringReadCursorCreator(mColumn, mResultVariableName, mCursorVariableName);
    }
  }
}
