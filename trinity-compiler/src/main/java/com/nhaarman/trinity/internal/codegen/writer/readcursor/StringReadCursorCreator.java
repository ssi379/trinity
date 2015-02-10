package com.nhaarman.trinity.internal.codegen.writer.readcursor;

import com.nhaarman.trinity.internal.codegen.data.Column;
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
  private final Column mColumn;

  @NotNull
  private final String mEntityVariableName;

  @NotNull
  private final String mCursorVariableName;

  public StringReadCursorCreator(@NotNull final Column column,
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
            mColumn.setter().getName(),
            mCursorVariableName,
            mCursorVariableName,
            mColumn.getName())
        .build();
  }
}
