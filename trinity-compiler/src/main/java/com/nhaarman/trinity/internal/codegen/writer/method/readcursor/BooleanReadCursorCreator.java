package com.nhaarman.trinity.internal.codegen.writer.method.readcursor;

import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.squareup.javapoet.CodeBlock;
import org.jetbrains.annotations.NotNull;

class BooleanReadCursorCreator implements ReadCursorCreator {

  /**
   * Resolves to:
   * <p/>
   * result.setDeleted(cursor.getBoolean(cursor.getColumnIndex("deleted")));
   */
  private static final String STATEMENT = "$L.$L($L.getInt($L.getColumnIndex($S)) == 1)";

  @NotNull
  private final ColumnMethod mColumn;

  @NotNull
  private final String mEntityVariableName;

  @NotNull
  private final String mCursorVariableName;

  BooleanReadCursorCreator(@NotNull final ColumnMethod column,
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
            mColumn.getColumnName())
        .build();
  }
}
