package com.nhaarman.trinity.internal.codegen.table.repository.writer;

import com.nhaarman.trinity.internal.codegen.table.TableClass;
import com.nhaarman.trinity.internal.codegen.table.repository.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.table.repository.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.table.repository.RepositoryMethod.Parameter;
import com.squareup.javapoet.MethodSpec;
import org.junit.Before;
import org.junit.Test;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.*;

public class FindCreatorTest {

  private static final String EXPECTED_CODE =
      ""
          + "if (id == null) {\n"
          + "  return null;\n"
          + "}\n"
          + "\n"
          + "MyConcreteType result = null;\n"
          + "\n"
          + "android.database.Cursor cursor = new com.nhaarman.trinity.query.Select().from(\"my_table\").where(\"id=?\", id).limit(\"1\").fetchFrom(mDatabase);\n"
          + "try {\n"
          + "  if (cursor.moveToFirst()) {\n"
          + "    result = readCursor(cursor);\n"
          + "  }\n"
          + "} finally{\n"
          + "  cursor.close();\n"
          + "}\n"
          + "\n"
          + "return result;\n"
          + "";

  private FindCreator mFindCreator;

  @Before
  public void setUp() {
    MethodSpec readCursorMethod = MethodSpec.methodBuilder("readCursor").build();

    TableClass tableClassMock = mock(TableClass.class);
    when(tableClassMock.getEntityFullyQualifiedName()).thenReturn("MyConcreteType");
    when(tableClassMock.getTableName()).thenReturn("my_table");

    RepositoryClass repositoryClassMock = mock(RepositoryClass.class);
    when(repositoryClassMock.getTableClass()).thenReturn(tableClassMock);

    Parameter parameterMock = mock(Parameter.class);
    when(parameterMock.getName()).thenReturn("id");
    when(parameterMock.getType()).thenReturn("java.lang.Long");

    RepositoryMethod repositoryMethodMock = mock(RepositoryMethod.class);
    when(repositoryMethodMock.getMethodName()).thenReturn("find");
    when(repositoryMethodMock.getParameter()).thenReturn(parameterMock);
    when(repositoryMethodMock.getReturnType()).thenReturn("MyClass");

    mFindCreator = new FindCreator(repositoryClassMock, readCursorMethod, repositoryMethodMock);
  }

  @Test
  public void createFindMethodSpec_returnsNotNull() {
    /* When */
    MethodSpec methodSpec = mFindCreator.create();

    /* Then */
    assertThat(methodSpec, is(not(nullValue())));
  }

  @Test
  public void createdMethodSpec_hasProperMethodName() {
    /* When */
    MethodSpec methodSpec = mFindCreator.create();

    /* Then */
    assertThat(methodSpec.name, is("find"));
  }

  @Test
  public void createdMethodSpec_hasOverrideAnnotation() {
    /* When */
    MethodSpec methodSpec = mFindCreator.create();

    /* Then */
    assertThat(methodSpec.annotations, is(not(empty())));
    assertThat(methodSpec.annotations.get(0).type.toString(), is("java.lang.Override"));
  }

  @Test
  public void createdMethodSpec_isPublic() {
    /* When */
    MethodSpec methodSpec = mFindCreator.create();

    /* Then */
    assertThat(methodSpec.modifiers, hasItem(PUBLIC));
  }

  @Test
  public void createdMethodSpec_hasASingleFinalParameterWithCorrectTypeAndName() {
    /* When */
    MethodSpec methodSpec = mFindCreator.create();

    /* Then */
    assertThat(methodSpec.parameters.size(), is(1));
    assertThat(methodSpec.parameters.get(0).modifiers, hasItem(FINAL));
    assertThat(methodSpec.parameters.get(0).type.toString(), is("java.lang.Long"));
    assertThat(methodSpec.parameters.get(0).name, is("id"));
  }

  @Test
  public void createdMethodSpec_returnsProperType() {
    /* When */
    MethodSpec methodSpec = mFindCreator.create();

    /* Then */
    assertThat(methodSpec.returnType.toString(), is("MyClass"));
  }

  @Test
  public void createdMethodSpec_hasCorrectCode() {
    /* When */
    MethodSpec methodSpec = mFindCreator.create();

    /* Then */
    assertThat(methodSpec.code.toString(), is(equalTo(EXPECTED_CODE)));
  }
}