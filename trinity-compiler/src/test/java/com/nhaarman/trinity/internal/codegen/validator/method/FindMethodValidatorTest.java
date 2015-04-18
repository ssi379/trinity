package com.nhaarman.trinity.internal.codegen.validator.method;

import com.nhaarman.trinity.internal.codegen.Message;
import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.Parameter;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.method.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.validator.ValidationHandler;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import org.junit.Before;
import org.junit.Test;

import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.ERROR;
import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class FindMethodValidatorTest {

  private static final String FULLY_QUALIFIED_TABLECLASSNAME = "TableClassPackageName.TableClassName";
  private static final String METHOD_NAME = "find";
  private static final String PRIMARY_KEY_METHOD_NAME = "setId";

  private FindMethodValidator mFindMethodValidator;
  private ColumnMethod mColumnMethodMock;
  private RepositoryMethod mRepositoryMethodMock;

  @Before
  public void setUp() {
    mColumnMethodMock = mock(ColumnMethod.class);
    when(mColumnMethodMock.getType()).thenReturn(Long.class.getName());
    when(mColumnMethodMock.getMethodName()).thenReturn(PRIMARY_KEY_METHOD_NAME);
    when(mColumnMethodMock.getFullyQualifiedTableClassName()).thenReturn(FULLY_QUALIFIED_TABLECLASSNAME);

    ColumnMethodRepository columnMethodRepository = new ColumnMethodRepository();
    columnMethodRepository.save(mColumnMethodMock);

    RepositoryClass repositoryClassMock = mock(RepositoryClass.class);
    when(repositoryClassMock.getTableClassFullyQualifiedName()).thenReturn(FULLY_QUALIFIED_TABLECLASSNAME);

    Parameter parameterMock = mock(Parameter.class);
    when(parameterMock.getType()).thenReturn(Long.class.getName());

    mRepositoryMethodMock = mock(RepositoryMethod.class);
    when(mRepositoryMethodMock.getMethodName()).thenReturn(METHOD_NAME);
    when(mRepositoryMethodMock.getParameter()).thenReturn(parameterMock);

    mFindMethodValidator = new FindMethodValidator(columnMethodRepository, repositoryClassMock);
  }

  @Test
  public void aRepositoryClassWithAFindMethod_forATableWithPrimaryKeyGetterWithProperType_validates() {
    /* Given */
    when(mColumnMethodMock.isGetter()).thenReturn(true);
    when(mColumnMethodMock.isPrimary()).thenReturn(true);

    ValidationHandler validationHandlerMock = mock(ValidationHandler.class);

    /* When */
    ProcessingStepResult result = mFindMethodValidator.validate(mRepositoryMethodMock, validationHandlerMock);

    /* Then */
    verifyNoMoreInteractions(validationHandlerMock);
    assertThat(result, is(OK));
  }

  @Test
  public void aRepositoryClassWithAFindMethod_forATableWithPrimaryKeySetterWithProperType_validates() {
    /* Given */
    when(mColumnMethodMock.isGetter()).thenReturn(false);
    when(mColumnMethodMock.isPrimary()).thenReturn(true);

    ValidationHandler validationHandlerMock = mock(ValidationHandler.class);

    /* When */
    ProcessingStepResult result = mFindMethodValidator.validate(mRepositoryMethodMock, validationHandlerMock);

    /* Then */
    verifyNoMoreInteractions(validationHandlerMock);
    assertThat(result, is(OK));
  }

  @Test
  public void aRepositoryClassWithAFindMethod_forATableWithoutAPrimaryKey_doesNotValidate() {
    /* Given */
    when(mColumnMethodMock.isPrimary()).thenReturn(false);
    ValidationHandler validationHandlerMock = mock(ValidationHandler.class);

    /* When */
    ProcessingStepResult result = mFindMethodValidator.validate(mRepositoryMethodMock, validationHandlerMock);

    /* Then */
    verify(validationHandlerMock).onError(
        any(Element.class),
        any(AnnotationMirror.class),
        eq(Message.MISSING_PRIMARYKEY_METHOD_FOR_FIND_IMPLEMENTATION),
        eq(METHOD_NAME),
        eq(FULLY_QUALIFIED_TABLECLASSNAME)
    );
    assertThat(result, is(ERROR));
  }

  @Test
  public void aRepositoryClassWithAFindMethod_forATableWithPrimaryKeyGetterWithDifferentType_doesNotValidate() {
    /* Given */
    when(mColumnMethodMock.isGetter()).thenReturn(true);
    when(mColumnMethodMock.isPrimary()).thenReturn(true);
    when(mColumnMethodMock.getType()).thenReturn(String.class.getName());

    ValidationHandler validationHandlerMock = mock(ValidationHandler.class);

    /* When */
    ProcessingStepResult result = mFindMethodValidator.validate(mRepositoryMethodMock, validationHandlerMock);

    /* Then */
    verify(validationHandlerMock).onError(
        any(Element.class),
        any(AnnotationMirror.class),
        eq(Message.PRIMARYKEY_FIND_TYPE_MISMATCH),
        eq(Long.class.getName()),
        eq(METHOD_NAME),
        eq(String.class.getName()),
        eq(FULLY_QUALIFIED_TABLECLASSNAME + '.' + PRIMARY_KEY_METHOD_NAME)
    );
    assertThat(result, is(ERROR));
  }

  @Test
  public void aRepositoryClassWithAFindMethod_forATableWithPrimaryKeySetterWithDifferentType_doesNotValidate() {
    /* Given */
    when(mColumnMethodMock.isPrimary()).thenReturn(true);
    when(mColumnMethodMock.getType()).thenReturn(String.class.getName());

    ValidationHandler validationHandlerMock = mock(ValidationHandler.class);

    /* When */
    ProcessingStepResult result = mFindMethodValidator.validate(mRepositoryMethodMock, validationHandlerMock);

    /* Then */
    verify(validationHandlerMock).onError(
        any(Element.class),
        any(AnnotationMirror.class),
        eq(Message.PRIMARYKEY_FIND_TYPE_MISMATCH),
        eq(Long.class.getName()),
        eq(METHOD_NAME),
        eq(String.class.getName()),
        eq(FULLY_QUALIFIED_TABLECLASSNAME + '.' + PRIMARY_KEY_METHOD_NAME)
    );
    assertThat(result, is(ERROR));
  }
}