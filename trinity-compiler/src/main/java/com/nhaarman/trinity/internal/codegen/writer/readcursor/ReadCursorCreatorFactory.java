package com.nhaarman.trinity.internal.codegen.writer.readcursor;

import com.nhaarman.trinity.internal.codegen.ProcessingException;
import com.nhaarman.trinity.internal.codegen.data.Column;
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

  public ReadCursorCreatorFactory(@NotNull final String resultVariableName,
                                  @NotNull final String cursorVariableName) {
    mResultVariableName = resultVariableName;
    mCursorVariableName = cursorVariableName;
  }

  public ReadCursorCreator createReadCursorCreator(@NotNull final Column column) throws ProcessingException {
    String typeString = column.getFullyQualifiedJavaType();

    ReadCursorCreator result;

    switch (typeString) {
      case "java.lang.String":
        result = new StringReadCursorCreator(column, mResultVariableName, mCursorVariableName);
        break;
      case "java.lang.Long":
        result = new LongReadCursorCreator(column, mResultVariableName, mCursorVariableName);
        break;
      default:
        throw new ProcessingException(String.format("Type %s is not supported.", typeString), column.getElement());
    }

    return result;
  }
}
