package com.nhaarman.trinity.internal.codegen.writer.method.readcursor;

import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.SerializerClass;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.jetbrains.annotations.NotNull;

class SerializedReadCursorCreator implements ReadCursorCreator {

  /**
   * Resolves to:
   * <p/>
   * result.setMyObject(new MyObjectSerializer().deserialize(cursor.getString(cursor.getColumnIndex("my_object"))));
   */
  private static final String STATEMENT = "$L.$L(new $T().deserialize($L.getString($L.getColumnIndex($S))))";

  @NotNull
  private final ColumnMethod mColumn;

  @NotNull
  private final String mEntityVariableName;

  @NotNull
  private final String mCursorVariableName;

  @NotNull
  private final SerializerClass mSerializerClass;

  SerializedReadCursorCreator(@NotNull final ColumnMethod column,
                              @NotNull final String entityVariableName,
                              @NotNull final String cursorVariableName,
                              @NotNull final SerializerClass serializerClass) {
    mColumn = column;
    mEntityVariableName = entityVariableName;
    mCursorVariableName = cursorVariableName;
    mSerializerClass = serializerClass;
  }

  @Override
  public CodeBlock create() {
    return CodeBlock.builder()
        .addStatement(
            STATEMENT,
            mEntityVariableName,
            mColumn.getMethodName(),
            ClassName.bestGuess(mSerializerClass.getFullyQualifiedClassName()),
            mCursorVariableName,
            mCursorVariableName,
            mColumn.getColumnName())
        .build();
  }
}
