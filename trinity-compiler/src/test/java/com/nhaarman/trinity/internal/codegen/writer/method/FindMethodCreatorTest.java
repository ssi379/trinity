package com.nhaarman.trinity.internal.codegen.writer.method;

import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod.Parameter;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import org.junit.Before;
import org.junit.Test;

import static com.nhaarman.trinity.internal.codegen.AndroidClasses.SQLITE_DATABASE;
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

public class FindMethodCreatorTest {

  private static final String DATABASE_FIELD = "mDatabase";
  private static final String TABLE_NAME = "my_table";
  private static final String RETURN_TYPE = "MyClass";
  private static final String PARAMETER_TYPE = "java.lang.Long";
  private static final String PARAMETER_NAME = "id";

  private static final String EXPECTED_JAVADOC = ""
      + "Performs a query for a " + RETURN_TYPE + " with given id.\n"
      + "If no such instance is found, null is returned.\n"
      + "\n"
      + "@param " + PARAMETER_NAME + " The id of the instance to find."
      + "\n"
      + "@return The " + RETURN_TYPE + " with given id, or null if it doesn't exist.";

  private static final String EXPECTED_CODE = ""
      + "if (id == null) {\n"
      + "  return null;\n"
      + "}\n"
      + "\n"
      + RETURN_TYPE + " result = null;\n"
      + "\n"
      + "android.database.Cursor cursor = new com.nhaarman.trinity.query.select.Select()."
      + "from(\"" + TABLE_NAME + "\").where(\"id=?\", id).limit(\"1\").queryOn(" + DATABASE_FIELD + ");\n"
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

  private FindMethodCreator mFindMethodCreator;

  @Before
  public void setUp() {
    FieldSpec databaseFieldSpec = FieldSpec.builder(SQLITE_DATABASE, DATABASE_FIELD).build();

    MethodSpec readCursorMethod = MethodSpec.methodBuilder("readCursor").build();

    TableClass tableClassMock = mock(TableClass.class);
    when(tableClassMock.getEntityFullyQualifiedName()).thenReturn(RETURN_TYPE);
    when(tableClassMock.getTableName()).thenReturn(TABLE_NAME);

    RepositoryClass repositoryClassMock = mock(RepositoryClass.class);
    when(repositoryClassMock.getTableClass()).thenReturn(tableClassMock);

    Parameter parameterMock = mock(Parameter.class);
    when(parameterMock.getName()).thenReturn(PARAMETER_NAME);
    when(parameterMock.getType()).thenReturn(PARAMETER_TYPE);

    RepositoryMethod repositoryMethodMock = mock(RepositoryMethod.class);
    when(repositoryMethodMock.getMethodName()).thenReturn("find");
    when(repositoryMethodMock.getParameter()).thenReturn(parameterMock);
    when(repositoryMethodMock.getReturnType()).thenReturn(RETURN_TYPE);

    mFindMethodCreator = new FindMethodCreator(repositoryClassMock, databaseFieldSpec, readCursorMethod, repositoryMethodMock);
  }

  @Test
  public void createFindMethodSpec_returnsNotNull() {
    /* When */
    MethodSpec methodSpec = mFindMethodCreator.create();

    /* Then */
    assertThat(methodSpec, is(not(nullValue())));
  }

  @Test
  public void createdMethodSpec_hasJavadoc() {
    /* When */
    MethodSpec methodSpec = mFindMethodCreator.create();

    /* Then */
    assertThat(methodSpec.javadoc.toString(), is(EXPECTED_JAVADOC));
  }

  @Test
  public void createdMethodSpec_hasProperMethodName() {
    /* When */
    MethodSpec methodSpec = mFindMethodCreator.create();

    /* Then */
    assertThat(methodSpec.name, is("find"));
  }

  @Test
  public void createdMethodSpec_hasOverrideAnnotation() {
    /* When */
    MethodSpec methodSpec = mFindMethodCreator.create();

    /* Then */
    assertThat(methodSpec.annotations, is(not(empty())));
    assertThat(methodSpec.annotations.get(0).type.toString(), is("java.lang.Override"));
  }

  @Test
  public void createdMethodSpec_isPublic() {
    /* When */
    MethodSpec methodSpec = mFindMethodCreator.create();

    /* Then */
    assertThat(methodSpec.modifiers, hasItem(PUBLIC));
  }

  @Test
  public void createdMethodSpec_hasASingleFinalParameterWithCorrectTypeAndName() {
    /* When */
    MethodSpec methodSpec = mFindMethodCreator.create();

    /* Then */
    assertThat(methodSpec.parameters.size(), is(1));
    assertThat(methodSpec.parameters.get(0).modifiers, hasItem(FINAL));
    assertThat(methodSpec.parameters.get(0).type.toString(), is("java.lang.Long"));
    assertThat(methodSpec.parameters.get(0).name, is("id"));
  }

  @Test
  public void createdMethodSpec_returnsProperType() {
    /* When */
    MethodSpec methodSpec = mFindMethodCreator.create();

    /* Then */
    assertThat(methodSpec.returnType.toString(), is(RETURN_TYPE));
  }

  @Test
  public void createdMethodSpec_hasCorrectCode() {
    /* When */
    MethodSpec methodSpec = mFindMethodCreator.create();

    /* Then */
    assertThat(methodSpec.code.toString(), is(equalTo(EXPECTED_CODE)));
  }
}