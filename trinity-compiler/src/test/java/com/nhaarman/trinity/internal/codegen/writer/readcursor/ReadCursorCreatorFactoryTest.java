package com.nhaarman.trinity.internal.codegen.writer.readcursor;

import com.nhaarman.trinity.internal.codegen.ProcessingException;
import com.nhaarman.trinity.internal.codegen.data.Column;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@SuppressWarnings("HardCodedStringLiteral")
public class ReadCursorCreatorFactoryTest {

  private ReadCursorCreatorFactory mReadCursorCreatorFactory;

  private Column mColumnMock;

  @Before
  public void setUp() {
    mColumnMock = mock(Column.class);
    mReadCursorCreatorFactory = new ReadCursorCreatorFactory("result", "cursor");
  }

  @Test
  public void javaLangString_returnsStringReadCursorCreator() throws ProcessingException {
    /* Given */
    when(mColumnMock.getFullyQualifiedJavaType()).thenReturn("java.lang.String");

    /* When */
    ReadCursorCreator readCursorCreator = mReadCursorCreatorFactory.createReadCursorCreator(mColumnMock);

    /* Then */
    assertThat(readCursorCreator, is(instanceOf(StringReadCursorCreator.class)));
  }

  @Test
  public void javaLangLong_returnsStringReadCursorCreator()  throws ProcessingException {
    /* Given */
    when(mColumnMock.getFullyQualifiedJavaType()).thenReturn("java.lang.Long");

    /* When */
    ReadCursorCreator readCursorCreator = mReadCursorCreatorFactory.createReadCursorCreator(mColumnMock);

    /* Then */
    assertThat(readCursorCreator, is(instanceOf(LongReadCursorCreator.class)));
  }

  @Test(expected = ProcessingException.class)
  public void unknownType_throwsProcessingException() throws ProcessingException  {
    /* Given */
    when(mColumnMock.getFullyQualifiedJavaType()).thenReturn("some.type.that.does.not.Exist");

    /* When */
    mReadCursorCreatorFactory.createReadCursorCreator(mColumnMock);
  }
}