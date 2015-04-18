package com.nhaarman.trinity.internal.codegen.validator.method;

import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.validator.Validator;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.*;

public class MethodValidatorFactoryTest {

  private MethodValidatorFactory mMethodValidatorFactory;

  @Before
  public void setUp() {
    mMethodValidatorFactory = new MethodValidatorFactory(mock(ColumnMethodRepository.class), mock(RepositoryClass.class));
  }

  @Test
  public void requestingAValidatorForFindMethod_returnsProperValidator() {
    /* Given */
    RepositoryMethod repositoryMethod = mock(RepositoryMethod.class);
    when(repositoryMethod.getMethodName()).thenReturn("find");

    /* When */
    Validator<RepositoryMethod> result = mMethodValidatorFactory.validatorFor(repositoryMethod);

    /* Then */
    assertThat(result, is(instanceOf(FindMethodValidator.class)));
  }

  @Test
  public void requestingAValidatorForFindByIdMethod_returnsProperValidator() {
    /* Given */
    RepositoryMethod repositoryMethod = mock(RepositoryMethod.class);
    when(repositoryMethod.getMethodName()).thenReturn("findById");

    /* When */
    Validator<RepositoryMethod> result = mMethodValidatorFactory.validatorFor(repositoryMethod);

    /* Then */
    assertThat(result, is(instanceOf(FindMethodValidator.class)));
  }

  @Test
  public void requestingAValidatorForCreateMethod_returnsProperValidator() {
    /* Given */
    RepositoryMethod repositoryMethod = mock(RepositoryMethod.class);
    when(repositoryMethod.getMethodName()).thenReturn("create");

    /* When */
    Validator<RepositoryMethod> result = mMethodValidatorFactory.validatorFor(repositoryMethod);

    /* Then */
    assertThat(result, is(instanceOf(CreateMethodValidator.class)));
  }

  @Test
  public void requestingAValidatorForAnUnknownMethodName_returnsNull() {
    /* Given */
    RepositoryMethod repositoryMethod = mock(RepositoryMethod.class);
    when(repositoryMethod.getMethodName()).thenReturn("SomeUnkownType");

    /* When */
    Validator<RepositoryMethod> result = mMethodValidatorFactory.validatorFor(repositoryMethod);

    /* Then */
    assertThat(result, is(nullValue()));
  }
}