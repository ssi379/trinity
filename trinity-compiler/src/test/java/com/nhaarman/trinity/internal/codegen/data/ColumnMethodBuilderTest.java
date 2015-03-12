package com.nhaarman.trinity.internal.codegen.data;

import com.nhaarman.trinity.internal.codegen.data.ColumnMethod.Builder;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

public class ColumnMethodBuilderTest {

  @Test(expected = IllegalStateException.class)
  public void missingName_throwsException() {
    new Builder()
        .withType(mock(TypeMirror.class))
        .withElement(mock(Element.class))
        .isPrimary()
        .build();
  }

  @Test(expected = IllegalStateException.class)
  public void missingType_throwsException() {
    new Builder()
        .withName("Name")
        .withElement(mock(Element.class))
        .isPrimary()
        .build();
  }

  @Test(expected = IllegalStateException.class)
  public void missingElement_throwsException() {
    new Builder()
        .withName("Name")
        .withType(mock(TypeMirror.class))
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

    /* When */
    ColumnMethod columnMethod = new Builder()
        .withName("Name")
        .withType(typeMirror)
        .withElement(mock(Element.class))
        .isPrimary()
        .build();

    /* Then */
    assertThat(columnMethod.getType(), is(typeMirror));
  }

  @Test
  public void createdGetterColumnMethod_isGetter() {
    /* When */
    ColumnMethod columnMethod = new Builder()
        .withName("Name")
        .withType(mock(TypeMirror.class))
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
        .withElement(mock(Element.class))
        .isPrimary()
        .isSetter()
        .build();

    /* Then */
    assertThat(columnMethod.isSetter(), is(true));
  }

  @Test
  public void createdPrimaryColumnMethod_isPrimary() {
    /* When */
    ColumnMethod columnMethod = new Builder()
        .withName("Name")
        .withType(mock(TypeMirror.class))
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
        .withElement(element)
        .isPrimary()
        .build();

    /* Then */
    assertThat(columnMethod.getElement(), is(element));
  }
}