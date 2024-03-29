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

import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.Parameter;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
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

  public static final String FIND = "find";
  private static final String DATABASE_FIELD = "mDatabase";
  private static final String TABLE_NAME = "my_table";
  private static final String RETURN_TYPE = "MyClass";
  private static final String PACKAGE = "mypackage";
  private static final String PARAMETER_TYPE = "java.lang.Long";
  private static final String PARAMETER_NAME = "id";
  private static final String COLUMN_NAME = "id";
  private static final String CUSTOM_PARAMETER_NAME = "key";
  private static final String CUSTOM_COLUMN_NAME = "key";

  private static final String EXPECTED_JAVADOC = ""
      + "Performs a query for a " + RETURN_TYPE + " with given id.\n"
      + "If no such instance is found, null is returned.\n"
      + '\n'
      + "@param " + PARAMETER_NAME + " The id of the instance to find."
      + '\n'
      + "@return The " + RETURN_TYPE + " with given id, or null if it doesn't exist.";

  private static final String EXPECTED_CODE = ""
      + "if (" + PARAMETER_NAME + " == null) {\n"
      + "  return null;\n"
      + "}\n"
      + '\n'
      + PACKAGE + '.' + RETURN_TYPE + " result = null;\n"
      + '\n'
      + "android.database.Cursor cursor = mDatabase.query(\""+TABLE_NAME+"\", null, \"" + COLUMN_NAME + "=?\", new String[] {String.valueOf("+PARAMETER_NAME+")}, null, null, null, \"1\");\n"
      + "try {\n"
      + "  if (cursor.moveToFirst()) {\n"
      + "    result = readCursor(cursor);\n"
      + "  }\n"
      + "} finally{\n"
      + "  cursor.close();\n"
      + "}\n"
      + '\n'
      + "return result;\n"
      + "";

  private static final String EXPECTED_CODE_CUSTOM_PARAMETER_NAME = ""
      + "if (" + CUSTOM_PARAMETER_NAME + " == null) {\n"
      + "  return null;\n"
      + "}\n"
      + '\n'
      + PACKAGE + '.' + RETURN_TYPE + " result = null;\n"
      + '\n'
      + "android.database.Cursor cursor = mDatabase.query(\""+TABLE_NAME+"\", null, \"" + COLUMN_NAME + "=?\", new String[] {String.valueOf("+CUSTOM_PARAMETER_NAME+")}, null, null, null, \"1\");\n"
      + "try {\n"
      + "  if (cursor.moveToFirst()) {\n"
      + "    result = readCursor(cursor);\n"
      + "  }\n"
      + "} finally{\n"
      + "  cursor.close();\n"
      + "}\n"
      + '\n'
      + "return result;\n"
      + "";

  private static final String EXPECTED_CODE_WITH_CUSTOM_COLUMN_NAME = ""
      + "if (" + PARAMETER_NAME + " == null) {\n"
      + "  return null;\n"
      + "}\n"
      + '\n'
      + PACKAGE + '.' + RETURN_TYPE + " result = null;\n"
      + '\n'
      + "android.database.Cursor cursor = mDatabase.query(\""+TABLE_NAME+"\", null, \"" + CUSTOM_COLUMN_NAME + "=?\", new String[] {String.valueOf("+PARAMETER_NAME+")}, null, null, null, \"1\");\n"
      + "try {\n"
      + "  if (cursor.moveToFirst()) {\n"
      + "    result = readCursor(cursor);\n"
      + "  }\n"
      + "} finally{\n"
      + "  cursor.close();\n"
      + "}\n"
      + '\n'
      + "return result;\n"
      + "";

  private FindMethodCreator mFindMethodCreator;

  private Parameter mParameterMock;
  private ColumnMethod mPrimaryKeyGetterMock;

  @Before
  public void setUp() {
    FieldSpec databaseFieldSpec = FieldSpec.builder(SQLITE_DATABASE, DATABASE_FIELD).build();

    MethodSpec readCursorMethod = MethodSpec.methodBuilder("readCursor").build();

    TableClass tableClassMock = mock(TableClass.class);
    when(tableClassMock.getClassName()).thenReturn(RETURN_TYPE);
    when(tableClassMock.getPackageName()).thenReturn(PACKAGE);
    when(tableClassMock.getTableName()).thenReturn(TABLE_NAME);

    mParameterMock = mock(Parameter.class);
    when(mParameterMock.getVariableName()).thenReturn(PARAMETER_NAME);
    when(mParameterMock.getFullyQualifiedType()).thenReturn(PARAMETER_TYPE);

    RepositoryMethod repositoryMethodMock = mock(RepositoryMethod.class);
    when(repositoryMethodMock.getMethodName()).thenReturn(FIND);
    when(repositoryMethodMock.getParameter()).thenReturn(mParameterMock);
    when(repositoryMethodMock.getReturnType()).thenReturn(RETURN_TYPE);

    mPrimaryKeyGetterMock = mock(ColumnMethod.class);
    when(mPrimaryKeyGetterMock.getColumnName()).thenReturn(COLUMN_NAME);

    mFindMethodCreator = new FindMethodCreator(tableClassMock, databaseFieldSpec, readCursorMethod, repositoryMethodMock, mPrimaryKeyGetterMock);
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
    assertThat(methodSpec.name, is(FIND));
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
    assertThat(methodSpec.parameters.get(0).name, is(PARAMETER_NAME));
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

  @Test
  public void createdMethodSpec_forTableWithStringKey_hasASingleFinalParameterWithCorrectTypeAndName() {
    /* Given */
    when(mParameterMock.getFullyQualifiedType()).thenReturn(String.class.getName());

    /* When */
    MethodSpec methodSpec = mFindMethodCreator.create();

    /* Then */
    assertThat(methodSpec.parameters.size(), is(1));
    assertThat(methodSpec.parameters.get(0).modifiers, hasItem(FINAL));
    assertThat(methodSpec.parameters.get(0).type.toString(), is("java.lang.String"));
    assertThat(methodSpec.parameters.get(0).name, is(PARAMETER_NAME));
  }

  @Test
  public void createdMethodSpec_withCustomParameterName_hascorrectCode() {
    /* Given */
    when(mParameterMock.getVariableName()).thenReturn(CUSTOM_PARAMETER_NAME);

    /* When */
    MethodSpec methodSpec = mFindMethodCreator.create();

    /* Then */
    assertThat(methodSpec.code.toString(), is(equalTo(EXPECTED_CODE_CUSTOM_PARAMETER_NAME)));
  }

  @Test
  public void createdMethodSpec_withCustomColumnName_hascorrectCode() {
    /* Given */
    when(mPrimaryKeyGetterMock.getColumnName()).thenReturn(CUSTOM_COLUMN_NAME);

    /* When */
    MethodSpec methodSpec = mFindMethodCreator.create();

    /* Then */
    assertThat(methodSpec.code.toString(), is(equalTo(EXPECTED_CODE_WITH_CUSTOM_COLUMN_NAME)));
  }
}