package com.nhaarman.trinity.internal.codegen.validator.method;

import com.nhaarman.trinity.internal.codegen.Message;
import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import com.nhaarman.trinity.internal.codegen.data.Parameter;
import com.nhaarman.trinity.internal.codegen.method.CreateMethod;
import com.nhaarman.trinity.internal.codegen.method.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.validator.ValidationHandler;
import java.util.Arrays;
import java.util.Collections;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class CreateMethodValidatorTest {

  private CreateMethodValidator mCreateMethodValidator;

  private ValidationHandler mValidationHandlerMock;

  @Before
  public void setUp() {
    mCreateMethodValidator = new CreateMethodValidator();
    mValidationHandlerMock = mock(ValidationHandler.class);
  }

  @Test
  public void validate_javaLangLong_create_validates() {
    /* Given */
    RepositoryMethod repositoryMethod =
        new CreateMethod(
            "create",
            "java.lang.Long",
            Collections.singletonList(mock(Parameter.class)),
            mock(Element.class)
        );

    /* When */
    ProcessingStepResult result = mCreateMethodValidator.validate(repositoryMethod, mValidationHandlerMock);

    /* Then */
    assertThat(result, is(ProcessingStepResult.OK));
    verifyNoMoreInteractions(mValidationHandlerMock);
  }

  @Test
  public void validate_void_create_doesNotValidate() {
    /* Given */
    RepositoryMethod repositoryMethod =
        new CreateMethod(
            "create",
            "void",
            Collections.singletonList(mock(Parameter.class)),
            mock(Element.class)
        );

    /* When */
    ProcessingStepResult result = mCreateMethodValidator.validate(repositoryMethod, mValidationHandlerMock);

    /* Then */
    assertThat(result, is(ProcessingStepResult.ERROR));
    verify(mValidationHandlerMock).onError(any(Element.class), any(AnnotationMirror.class), eq(Message.CREATE_METHOD_MUST_RETURN_LONG));
  }

  @Test
  public void validate_long_create_doesNotValidate() {
    /* Given */
    RepositoryMethod repositoryMethod =
        new CreateMethod(
            "create",
            "long",
            Collections.singletonList(mock(Parameter.class)),
            mock(Element.class)
        );

    /* When */
    ProcessingStepResult result = mCreateMethodValidator.validate(repositoryMethod, mValidationHandlerMock);

    /* Then */
    assertThat(result, is(ProcessingStepResult.ERROR));
    verify(mValidationHandlerMock).onError(any(Element.class), any(AnnotationMirror.class), eq(Message.CREATE_METHOD_MUST_RETURN_LONG));
  }

  @Test
  public void validate_withoutParameters_doesNotValidate() {
    /* Given */
    RepositoryMethod repositoryMethod =
        new CreateMethod(
            "create",
            "java.lang.Long",
            Collections.<Parameter>emptyList(),
            mock(Element.class)
        );

    /* When */
    ProcessingStepResult result = mCreateMethodValidator.validate(repositoryMethod, mValidationHandlerMock);

    /* Then */
    assertThat(result, is(ProcessingStepResult.ERROR));
    verify(mValidationHandlerMock).onError(any(Element.class), any(AnnotationMirror.class), eq(Message.CREATE_METHOD_MUST_RECEIVE_EXACTLY_ONE_PARAMETER));
  }

  @Test
  public void validate_withTwoParameters_doesNotValidate() {
    /* Given */
    RepositoryMethod repositoryMethod =
        new CreateMethod(
            "create",
            "java.lang.Long",
            Arrays.asList(mock(Parameter.class), mock(Parameter.class)),
            mock(Element.class)
        );

    /* When */
    ProcessingStepResult result = mCreateMethodValidator.validate(repositoryMethod, mValidationHandlerMock);

    /* Then */
    assertThat(result, is(ProcessingStepResult.ERROR));
    verify(mValidationHandlerMock).onError(any(Element.class), any(AnnotationMirror.class), eq(Message.CREATE_METHOD_MUST_RECEIVE_EXACTLY_ONE_PARAMETER));
  }
}