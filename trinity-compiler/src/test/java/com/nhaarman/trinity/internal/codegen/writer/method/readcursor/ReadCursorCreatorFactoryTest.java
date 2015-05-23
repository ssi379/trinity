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

import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.SerializerClassRepository;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@SuppressWarnings("HardCodedStringLiteral")
public class ReadCursorCreatorFactoryTest {

  private ReadCursorCreatorFactory mReadCursorCreatorFactory;

  private ColumnMethod mColumnMock;

  @Before
  public void setUp() {
    mColumnMock = mock(ColumnMethod.class);
    mReadCursorCreatorFactory = new ReadCursorCreatorFactory("result", "cursor", new SerializerClassRepository());
  }

  @Test
  public void javaLangString_returnsStringReadCursorCreator() {
    /* Given */
    when(mColumnMock.getType()).thenReturn("java.lang.String");

    /* When */
    ReadCursorCreator readCursorCreator = mReadCursorCreatorFactory.createReadCursorCreator(mColumnMock);

    /* Then */
    assertThat(readCursorCreator, is(instanceOf(StringReadCursorCreator.class)));
  }

  @Test
  public void javaLangLong_returnsStringReadCursorCreator() {
    /* Given */
    when(mColumnMock.getType()).thenReturn("java.lang.Long");

    /* When */
    ReadCursorCreator readCursorCreator = mReadCursorCreatorFactory.createReadCursorCreator(mColumnMock);

    /* Then */
    assertThat(readCursorCreator, is(instanceOf(LongReadCursorCreator.class)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void unknownType_throwsProcessingException() {
    /* Given */
    when(mColumnMock.getType()).thenReturn("some.type.that.does.not.Exist");

    /* When */
    mReadCursorCreatorFactory.createReadCursorCreator(mColumnMock);
  }
}