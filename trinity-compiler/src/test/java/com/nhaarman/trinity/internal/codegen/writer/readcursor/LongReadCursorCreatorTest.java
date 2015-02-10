package com.nhaarman.trinity.internal.codegen.writer.readcursor;

import com.nhaarman.trinity.internal.codegen.data.Column;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.squareup.javapoet.CodeBlock;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.*;

public class LongReadCursorCreatorTest {

  private static final String RESULT = "result";
  private static final String CURSOR = "cursor";

  private static final String SETTER_NAME = "setId";
  private static final String COLUMN_NAME = "id";

  private static final String EXPECTED_CODE = "result.setId(cursor.getLong(cursor.getColumnIndex(\"id\")));\n";

  private LongReadCursorCreator mLongReadCursorCreator;

  @Before
  public void setUp() {
    ColumnMethod setterMock = mock(ColumnMethod.class);
    when(setterMock.getName()).thenReturn(SETTER_NAME);

    Column columnMock = mock(Column.class);
    when(columnMock.setter()).thenReturn(setterMock);
    when(columnMock.getName()).thenReturn(COLUMN_NAME);

    mLongReadCursorCreator = new LongReadCursorCreator(columnMock, RESULT, CURSOR);
  }

  @Test
  public void create_returnsNotNull() {
    /* When */
    CodeBlock codeBlock = mLongReadCursorCreator.create();

    /* Then */
    assertThat(codeBlock, is(not(nullValue())));
  }

  @Test
  public void createdCodeBlock_hasCorrectCode() {
    /* When */
    CodeBlock codeBlock = mLongReadCursorCreator.create();
    String code = codeBlock.toString();

    /* Then */
    assertThat(code, is(equalTo(EXPECTED_CODE)));
  }
}