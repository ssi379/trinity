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

package com.nhaarman.trinity.internal.codegen.writer.method;

import com.nhaarman.trinity.internal.codegen.ProcessingException;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import org.junit.Before;
import org.junit.Test;

import static com.nhaarman.trinity.internal.codegen.AndroidClasses.SQLITE_DATABASE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MethodCreatorFactoryTest {

  private MethodCreatorFactory mMethodCreatorFactory;

  private RepositoryMethod mMethod;

  @Before
  public void setUp() {
    mMethod = mock(RepositoryMethod.class);

    FieldSpec databaseFieldSpec = FieldSpec.builder(SQLITE_DATABASE, "mDatabase").build();
    MethodSpec readCursorSpec = MethodSpec.methodBuilder("createContentValues").build();
    MethodSpec createContentValuesSpec = MethodSpec.methodBuilder("createContentValues").build();
    mMethodCreatorFactory = new MethodCreatorFactory(mock(TableClass.class), databaseFieldSpec, readCursorSpec, createContentValuesSpec, mock(ColumnMethod.class), mock(ColumnMethod.class));
  }

  @Test
  public void find_returnsFindCreator() throws ProcessingException {
    /* Given */
    when(mMethod.getMethodName()).thenReturn("find");

    /* When */
    MethodCreator creator = mMethodCreatorFactory.creatorFor(mMethod);

    /* Then */
    assertThat(creator, is(instanceOf(FindMethodCreator.class)));
  }

  @Test
  public void findById_returnsFindCreator() throws ProcessingException {
    /* Given */
    when(mMethod.getMethodName()).thenReturn("findById");

    /* When */
    MethodCreator creator = mMethodCreatorFactory.creatorFor(mMethod);

    /* Then */
    assertThat(creator, is(instanceOf(FindMethodCreator.class)));
  }

  @Test
  public void create_returnsCreateCreator() throws ProcessingException {
    /* Given */
    when(mMethod.getMethodName()).thenReturn("create");

    /* When */
    MethodCreator creator = mMethodCreatorFactory.creatorFor(mMethod);

    /* Then */
    assertThat(creator, is(instanceOf(CreateMethodCreator.class)));
  }

  @Test(expected = ProcessingException.class)
  public void unknownType_throwsProcessingException() throws ProcessingException {
    /* Given */
    when(mMethod.getMethodName()).thenReturn("some.unknown.Type");

    /* When */
    mMethodCreatorFactory.creatorFor(mMethod);
  }
}