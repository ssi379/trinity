package com.nhaarman.trinity.internal.codegen.data;

import com.nhaarman.trinity.internal.codegen.data.ColumnMethod.Builder;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ColumnMethodBuilderTest {

  @Test(expected = IllegalStateException.class)
  public void missingName_throwsException() {
    new Builder()
        .withType(mock(TypeMirror.class))
        .withElement(mock(Element.class))
        .withFullyQualifiedTableClassName("FullyQualifiedTableClassName")
        .withColumnName("ColumnName")
        .isPrimary()
        .build();
  }

  @Test(expected = IllegalStateException.class)
  public void missingType_throwsException() {
    new Builder()
        .withName("Name")
        .withElement(mock(Element.class))
        .withFullyQualifiedTableClassName("FullyQualifiedTableClassName")
        .withColumnName("ColumnName")
        .isPrimary()
        .build();
  }

  @Test(expected = IllegalStateException.class)
  public void missingElement_throwsException() {
    new Builder()
        .withName("Name")
        .withColumnName("ColumnName")
        .withType(mock(TypeMirror.class))
        .withFullyQualifiedTableClassName("FullyQualifiedTableClassName")
        .isPrimary()
        .build();
  }

  @Test(expected = IllegalStateException.class)
  public void missingFullyQualifiedTableClassName_throwsException() {
    new Builder()
        .withName("Name")
        .withColumnName("ColumnName")
        .withType(mock(TypeMirror.class))
        .withElement(mock(Element.class))
        .isPrimary()
        .build();
  }


  @Test(expected = IllegalStateException.class)
  public void missingColumnName_throwsException() {
    new Builder()
        .withName("Name")
        .withFullyQualifiedTableClassName("FullyQualifiedTableClassName")
        .withType(mock(TypeMirror.class))
        .withElement(mock(Element.class))
        .isPrimary()
        .build();
  }

  @Test
  public void createdColumnMethod_hasProperName() {
    /* Given */
    String name = "Name";

    /* When */
    ColumnMethod columnMethod = new Builder()
        .withName(name)
        .withType(mock(TypeMirror.class))
        .withColumnName("ColumnName")
        .withFullyQualifiedTableClassName("FullyQualifiedTableClassName")
        .withElement(mock(Element.class))
        .isPrimary()
        .build();

    /* Then */
    assertThat(columnMethod.getMethodName(), is(name));
  }

  @Test
  public void createdColumnMethod_hasProperType() {
    /* Given */
    TypeMirror typeMirror = mock(TypeMirror.class);
    when(typeMirror.toString()).thenReturn("myType");

    /* When */
    ColumnMethod columnMethod = new Builder()
        .withName("Name")
        .withType(typeMirror)
        .withColumnName("ColumnName")
        .withFullyQualifiedTableClassName("FullyQualifiedTableClassName")
        .withElement(mock(Element.class))
        .isPrimary()
        .build();

    /* Then */
    assertThat(columnMethod.getType(), is("myType"));
  }

  @Test
  public void createdGetterColumnMethod_isGetter() {
    /* When */
    ColumnMethod columnMethod = new Builder()
        .withName("Name")
        .withType(mock(TypeMirror.class))
        .withColumnName("ColumnName")
        .withFullyQualifiedTableClassName("FullyQualifiedTableClassName")
        .withElement(mock(Element.class))
        .isPrimary()
        .isGetter()
        .build();

    /* Then */
    assertThat(columnMethod.isGetter(), is(true));
  }

  @Test
  public void createdSetterColumnMethod_isSetter() {
    /* When */
    ColumnMethod columnMethod = new Builder()
        .withName("Name")
        .withType(mock(TypeMirror.class))
        .withColumnName("ColumnName")
        .withFullyQualifiedTableClassName("FullyQualifiedTableClassName")
        .withElement(mock(Element.class))
        .isPrimary()
        .isSetter()
        .build();

    /* Then */
    assertThat(columnMethod.isGetter(), is(false));
  }

  @Test
  public void createdPrimaryColumnMethod_isPrimary() {
    /* When */
    ColumnMethod columnMethod = new Builder()
        .withName("Name")
        .withType(mock(TypeMirror.class))
        .withColumnName("ColumnName")
        .withFullyQualifiedTableClassName("FullyQualifiedTableClassName")
        .withElement(mock(Element.class))
        .isPrimary()
        .build();

    /* Then */
    assertThat(columnMethod.isPrimary(), is(true));
  }

  @Test
  public void createdColumnMethod_hasProperElement() {
    /* Given */
    Element element = mock(Element.class);

    /* When */
    ColumnMethod columnMethod = new Builder()
        .withName("Name")
        .withType(mock(TypeMirror.class))
        .withColumnName("ColumnName")
        .withFullyQualifiedTableClassName("FullyQualifiedTableClassName")
        .withElement(element)
        .isPrimary()
        .build();

    /* Then */
    assertThat(columnMethod.getElement(), is(element));
  }
}