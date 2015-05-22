package com.nhaarman.trinity.internal.codegen.writer.method;

import com.nhaarman.trinity.internal.codegen.AndroidClasses;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import org.junit.Before;
import org.junit.Test;

import static javax.lang.model.element.Modifier.PUBLIC;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.*;

public class FindAllMethodCreatorTest {

  private static final String METHOD_NAME = "findAll";

  private static final String TABLE_NAME = "my_table";
  private static final String TABLE_TYPE = "MyEntity";

  private static final String RETURN_TYPE = "java.util.List";

  private static final String EXPECTED_JAVADOC = ""
      + "Performs a query to find all instances of " + TABLE_TYPE + ".\n"
      + '\n'
      + "@return A list which contains all instances of " + TABLE_TYPE + ".\n";

  private static final String EXPECTED_CODE = ""
      + "java.util.List results = new java.util.ArrayList<" + TABLE_TYPE + ">();\n"
      + '\n'
      + "android.database.Cursor cursor = mDatabase.query(\"my_table\", null, null, null, null, null, null);\n"
      + "try {\n"
      + "  while (cursor.moveToNext()) {\n"
      + "    results.add(readCursor(cursor));\n"
      + "  }\n"
      + "} finally{\n"
      + "  cursor.close();\n"
      + "}\n"
      + '\n'
      + "return results;\n";
  private static final String DATABASE_FIELD = "mDatabase";

  private FindAllMethodCreator mFindAllMethodCreator;

  @Before
  public void setUp() {
    MethodSpec readCursorSpec = MethodSpec.methodBuilder("readCursor").build();
    FieldSpec databaseFieldSpec = FieldSpec.builder(AndroidClasses.SQLITE_DATABASE, DATABASE_FIELD).build();

    RepositoryMethod methodMock = mock(RepositoryMethod.class);
    when(methodMock.getMethodName()).thenReturn(METHOD_NAME);
    when(methodMock.getReturnType()).thenReturn(RETURN_TYPE);

    TableClass tableClassMock = mock(TableClass.class);
    when(tableClassMock.getTableName()).thenReturn(TABLE_NAME);
    when(tableClassMock.getFullyQualifiedName()).thenReturn(TABLE_TYPE);

    mFindAllMethodCreator = new FindAllMethodCreator(tableClassMock, databaseFieldSpec, readCursorSpec, methodMock);
  }

  @Test
  public void create_returnsNotNull() {
    /* When */
    MethodSpec methodSpec = mFindAllMethodCreator.create();

    /* Then */
    assertThat(methodSpec, is(not(nullValue())));
  }

  @Test
  public void createdMethodSpec_hasJavadoc() {
    /* When */
    MethodSpec methodSpec = mFindAllMethodCreator.create();

    /* Then */
    assertThat(methodSpec.javadoc.toString(), is(EXPECTED_JAVADOC));
  }

  @Test
  public void createdMethodSpec_hasProperMethodName() {
    /* When */
    MethodSpec methodSpec = mFindAllMethodCreator.create();

    /* Then */
    assertThat(methodSpec.name, is(METHOD_NAME));
  }

  @Test
  public void createdMethodSpec_hasOverrideAnnotation() {
    /* When */
    MethodSpec methodSpec = mFindAllMethodCreator.create();

    /* Then */
    assertThat(methodSpec.annotations, is(not(empty())));
    assertThat(methodSpec.annotations.get(0).type.toString(), is("java.lang.Override"));
  }

  @Test
  public void createdMethodSpec_isPublic() {
    /* When */
    MethodSpec methodSpec = mFindAllMethodCreator.create();

    /* Then */
    assertThat(methodSpec.modifiers, hasItem(PUBLIC));
  }

  @Test
  public void createdMethodSpec_returnsProperType() {
    /* When */
    MethodSpec methodSpec = mFindAllMethodCreator.create();

    /* Then */
    assertThat(methodSpec.returnType.toString(), is(RETURN_TYPE));
  }

  @Test
  public void createdMethodSpec_hasCorrectCode() {
    /* When */
    MethodSpec methodSpec = mFindAllMethodCreator.create();

    /* Then */
    assertThat(methodSpec.code.toString(), is(equalTo(EXPECTED_CODE)));
  }
}