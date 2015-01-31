package com.nhaarman.trinity.internal.codegen.table.repository.writer;

import com.nhaarman.trinity.internal.codegen.table.repository.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.table.repository.RepositoryMethod;
import com.squareup.javapoet.MethodSpec;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.*;

public class CreatorFactoryTest {

  private CreatorFactory mCreatorFactory;

  @Before
  public void setUp() {
    MethodSpec readCursorSpec = MethodSpec.methodBuilder("createContentValues").build();
    MethodSpec createContentValuesSpec = MethodSpec.methodBuilder("createContentValues").build();
    mCreatorFactory = new CreatorFactory(mock(RepositoryClass.class), readCursorSpec, createContentValuesSpec);
  }

  @Test
  public void find_returnsFindCreator() {
    /* Given */
    RepositoryMethod method = mock(RepositoryMethod.class);
    when(method.getMethodName()).thenReturn("find");

    /* When */
    MethodCreator creator = mCreatorFactory.creatorFor(method);

    /* Then */
    assertThat(creator, is(instanceOf(FindCreator.class)));
  }

  @Test
  public void findById_returnsFindCreator() {
    /* Given */
    RepositoryMethod method = mock(RepositoryMethod.class);
    when(method.getMethodName()).thenReturn("findById");

    /* When */
    MethodCreator creator = mCreatorFactory.creatorFor(method);

    /* Then */
    assertThat(creator, is(instanceOf(FindCreator.class)));
  }

  @Test
  public void create_returnsCreateCreator() {
    /* Given */
    RepositoryMethod method = mock(RepositoryMethod.class);
    when(method.getMethodName()).thenReturn("create");

    /* When */
    MethodCreator creator = mCreatorFactory.creatorFor(method);

    /* Then */
    assertThat(creator, is(instanceOf(CreateCreator.class)));
  }

}