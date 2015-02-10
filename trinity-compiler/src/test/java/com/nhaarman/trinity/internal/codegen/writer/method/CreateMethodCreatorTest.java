package com.nhaarman.trinity.internal.codegen.writer.method;

import com.nhaarman.trinity.internal.codegen.data.Column;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod.Parameter;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
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

public class CreateMethodCreatorTest {

  private static final String METHOD_NAME = "create";
  private static final String PARAMETER_TYPE = "MyType";
  private static final String PARAMETER_NAME = "entity";
  private static final String TABLE_NAME = "my_table";

  private static final String RETURN_TYPE = "java.lang.Long";

  private static final String EXPECTED_JAVADOC = ""
      + "Executes an insert statement to persist given " + PARAMETER_TYPE + " in the database.\n"
      + "When successful, the id of the " + PARAMETER_TYPE + " will be set to the id of the created row.\n"
      + "\n"
      + "@param " + PARAMETER_NAME + " The " + PARAMETER_TYPE + " to insert.\n"
      + "\n"
      + "@return The created row id, or null if an error occurred.\n"
      + "";

  private static final String EXPECTED_CODE = ""
      + "java.lang.Long result = null;\n"
      + "\n"
      + "android.content.ContentValues contentValues = createContentValues(entity);\n"
      + "long id = mDatabase.insert(\"" + TABLE_NAME + "\", null, contentValues);\n"
      + "if (id != -1) {\n"
      + "  entity.setId(id);\n"
      + "  result = id;\n"
      + "}\n"
      + "\n"
      + "return result;\n"
      + "";

  private CreateMethodCreator mCreateMethodCreator;

  @Before
  public void setUp() {
    MethodSpec createContentValuesSpec = MethodSpec.methodBuilder("createContentValues").build();

    Parameter parameterMock = mock(Parameter.class);
    when(parameterMock.getType()).thenReturn(PARAMETER_TYPE);
    when(parameterMock.getName()).thenReturn(PARAMETER_NAME);

    RepositoryMethod methodMock = mock(RepositoryMethod.class);
    when(methodMock.getMethodName()).thenReturn(METHOD_NAME);
    when(methodMock.getParameter()).thenReturn(parameterMock);
    when(methodMock.getReturnType()).thenReturn(RETURN_TYPE);

    ColumnMethod setterMock = mock(ColumnMethod.class);
    when(setterMock.getName()).thenReturn("setId");

    Column primaryColumnMock = mock(Column.class);
    when(primaryColumnMock.setter()).thenReturn(setterMock);

    TableClass tableClassMock = mock(TableClass.class);
    when(tableClassMock.getTableName()).thenReturn(TABLE_NAME);
    when(tableClassMock.getPrimaryKeyColumn()).thenReturn(primaryColumnMock);

    RepositoryClass repositoryClassMock = mock(RepositoryClass.class);
    when(repositoryClassMock.getTableClass()).thenReturn(tableClassMock);

    mCreateMethodCreator = new CreateMethodCreator(repositoryClassMock, createContentValuesSpec, methodMock);
  }

  @Test
  public void create_returnsNotNull() {
    /* When */
    MethodSpec methodSpec = mCreateMethodCreator.create();

    /* Then */
    assertThat(methodSpec, is(not(nullValue())));
  }

  @Test
  public void createdMethodSpec_hasJavadoc() {
    /* When */
    MethodSpec methodSpec = mCreateMethodCreator.create();

    /* Then */
    assertThat(methodSpec.javadoc.toString(), is(EXPECTED_JAVADOC));
  }

  @Test
  public void createdMethodSpec_hasProperMethodName() {
    /* When */
    MethodSpec methodSpec = mCreateMethodCreator.create();

    /* Then */
    assertThat(methodSpec.name, is(METHOD_NAME));
  }

  @Test
  public void createdMethodSpec_hasOverrideAnnotation() {
    /* When */
    MethodSpec methodSpec = mCreateMethodCreator.create();

    /* Then */
    assertThat(methodSpec.annotations, is(not(empty())));
    assertThat(methodSpec.annotations.get(0).type.toString(), is("java.lang.Override"));
  }

  @Test
  public void createdMethodSpec_isPublic() {
    /* When */
    MethodSpec methodSpec = mCreateMethodCreator.create();

    /* Then */
    assertThat(methodSpec.modifiers, hasItem(PUBLIC));
  }

  @Test
  public void createdMethodSpec_hasASingleFinalParameterWithCorrectTypeAndName() {
    /* When */
    MethodSpec methodSpec = mCreateMethodCreator.create();

    /* Then */
    assertThat(methodSpec.parameters.size(), is(1));
    assertThat(methodSpec.parameters.get(0).modifiers, hasItem(FINAL));
    assertThat(methodSpec.parameters.get(0).type.toString(), is(PARAMETER_TYPE));
    assertThat(methodSpec.parameters.get(0).name, is(PARAMETER_NAME));
  }

  @Test
  public void createdMethodSpec_returnsProperType() {
    /* When */
    MethodSpec methodSpec = mCreateMethodCreator.create();

    /* Then */
    assertThat(methodSpec.returnType.toString(), is(RETURN_TYPE));
  }

  @Test
  public void createdMethodSpec_hasCorrectCode() {
    /* When */
    MethodSpec methodSpec = mCreateMethodCreator.create();

    /* Then */
    assertThat(methodSpec.code.toString(), is(equalTo(EXPECTED_CODE)));
  }
}