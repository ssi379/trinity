package com.nhaarman.trinity.internal.codegen.writer.method;

import android.database.sqlite.SQLiteDatabase;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.squareup.javapoet.FieldSpec;
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
    FieldSpec databaseFieldSpec = FieldSpec.builder(SQLiteDatabase.class, "mDatabase").build();
    MethodSpec readCursorSpec = MethodSpec.methodBuilder("createContentValues").build();
    MethodSpec createContentValuesSpec = MethodSpec.methodBuilder("createContentValues").build();
    mCreatorFactory = new CreatorFactory(mock(RepositoryClass.class), databaseFieldSpec, readCursorSpec, createContentValuesSpec);
  }

  @Test
  public void find_returnsFindCreator() {
    /* Given */
    RepositoryMethod method = mock(RepositoryMethod.class);
    when(method.getMethodName()).thenReturn("find");

    /* When */
    MethodCreator creator = mCreatorFactory.creatorFor(method);

    /* Then */
    assertThat(creator, is(instanceOf(FindMethodCreator.class)));
  }

  @Test
  public void findById_returnsFindCreator() {
    /* Given */
    RepositoryMethod method = mock(RepositoryMethod.class);
    when(method.getMethodName()).thenReturn("findById");

    /* When */
    MethodCreator creator = mCreatorFactory.creatorFor(method);

    /* Then */
    assertThat(creator, is(instanceOf(FindMethodCreator.class)));
  }

  @Test
  public void create_returnsCreateCreator() {
    /* Given */
    RepositoryMethod method = mock(RepositoryMethod.class);
    when(method.getMethodName()).thenReturn("create");

    /* When */
    MethodCreator creator = mCreatorFactory.creatorFor(method);

    /* Then */
    assertThat(creator, is(instanceOf(CreateMethodCreator.class)));
  }
}