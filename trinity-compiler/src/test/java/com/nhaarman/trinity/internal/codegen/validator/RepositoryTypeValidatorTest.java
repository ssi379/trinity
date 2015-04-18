package com.nhaarman.trinity.internal.codegen.validator;

import com.nhaarman.trinity.internal.codegen.Message;
import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import java.util.HashSet;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import org.junit.Before;
import org.junit.Test;

import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.ERROR;
import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class RepositoryTypeValidatorTest {

  private RepositoryTypeValidator mRepositoryTypeValidator;

  @Before
  public void setUp() {
    mRepositoryTypeValidator = new RepositoryTypeValidator();
  }

  @Test
  public void validatingClassElements_validatesSuccessfully() {
    /* Given */
    Set<Element> elements = new HashSet<>();
    for (int i = 0; i < 5; i++) {
      Element element = mock(Element.class);
      when(element.getKind()).thenReturn(ElementKind.CLASS);
      elements.add(element);
    }
    ValidationHandler validationHandlerMock = mock(ValidationHandler.class);

    /* When */
    ProcessingStepResult result = mRepositoryTypeValidator.validate(elements, validationHandlerMock);

    /* Then */
    verifyNoMoreInteractions(validationHandlerMock);
    assertThat(result, is(OK));
  }

  @Test
  public void validatingInterfaceElements_validatesSuccessfully() {
    /* Given */
    Set<Element> elements = new HashSet<>();
    for (int i = 0; i < 5; i++) {
      Element element = mock(Element.class);
      when(element.getKind()).thenReturn(ElementKind.INTERFACE);
      elements.add(element);
    }
    ValidationHandler validationHandlerMock = mock(ValidationHandler.class);

    /* When */
    ProcessingStepResult result = mRepositoryTypeValidator.validate(elements, validationHandlerMock);

    /* Then */
    verifyNoMoreInteractions(validationHandlerMock);
    assertThat(result, is(OK));
  }


  @Test
  public void validatingANonClassOrInterfaceElement_doesNotValidate() {
    /* Given */
    Set<Element> elements = new HashSet<>();
    for (int i = 0; i < 5; i++) {
      Element elementMock = mock(Element.class);
      when(elementMock.getKind()).thenReturn(ElementKind.METHOD);
      elements.add(elementMock);
    }
    ValidationHandler validationHandlerMock = mock(ValidationHandler.class);

    /* When */
    ProcessingStepResult result = mRepositoryTypeValidator.validate(elements, validationHandlerMock);

    /* Then */
    assertThat(result, is(ERROR));
    verify(validationHandlerMock, times(5)).onError(any(Element.class), any(AnnotationMirror.class), eq(Message.REPOSITORY_ANNOTATION_CAN_ONLY_BE_APPLIED_TO_CLASSES_OR_INTERFACES));
  }
}