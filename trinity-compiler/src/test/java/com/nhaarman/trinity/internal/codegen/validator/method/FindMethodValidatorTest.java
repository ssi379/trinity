package com.nhaarman.trinity.internal.codegen.validator.method;

import com.nhaarman.trinity.internal.codegen.ValidationException;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.Parameter;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class FindMethodValidatorTest {

  private FindMethodValidator mFindMethodValidator;
  private ColumnMethod mColumnMethodMock;
  private RepositoryMethod mRepositoryMethodMock;

  @Before
  public void setUp() {
    mColumnMethodMock = mock(ColumnMethod.class);
    when(mColumnMethodMock.getFullyQualifiedTableClassName()).thenReturn("TableClassPackageName.TableClassName");
    when(mColumnMethodMock.getType()).thenReturn(Long.class.getName());

    ColumnMethodRepository columnMethodRepository = new ColumnMethodRepository();
    columnMethodRepository.save(mColumnMethodMock);

    RepositoryClass repositoryClassMock = mock(RepositoryClass.class);
    when(repositoryClassMock.getTableClassFullyQualifiedName()).thenReturn("TableClassPackageName.TableClassName");

    Parameter parameterMock = mock(Parameter.class);
    when(parameterMock.getType()).thenReturn(Long.class.getName());

    mRepositoryMethodMock = mock(RepositoryMethod.class);
    when(mRepositoryMethodMock.getParameter()).thenReturn(parameterMock);

    mFindMethodValidator = new FindMethodValidator(columnMethodRepository, repositoryClassMock);
  }

  @Test(expected = ValidationException.class)
  public void aRepositoryClassWithAFindMethod_forATableWithoutAPrimaryKey_throwsAValidationException() throws ValidationException {
    /* Given */
    when(mColumnMethodMock.isPrimary()).thenReturn(false);

    /* When */
    mFindMethodValidator.validate(mRepositoryMethodMock);

    /* Then */
    // An exception is thrown.
  }

  @Test(expected = ValidationException.class)
  public void aRepositoryClassWithAFindMethod_forATableWithPrimaryKeyGetterWithDifferentType_throwsAnException() throws ValidationException {
    /* Given */
    when(mColumnMethodMock.isGetter()).thenReturn(true);
    when(mColumnMethodMock.isPrimary()).thenReturn(true);

    /* When */
    mFindMethodValidator.validate(mRepositoryMethodMock);

    /* Then */
    // We're happy
  }

  @Test(expected = ValidationException.class)
  public void aRepositoryClassWithAFindMethod_forATableWithPrimaryKeySetterWithDifferentType_throwsAnException() throws ValidationException {
    /* Given */

    /* When */
    mFindMethodValidator.validate(mRepositoryMethodMock);

    /* Then */
    // We're happy
  }

  @Test
  public void aRepositoryClassWithAFindMethod_forATableWithPrimaryKeyGetterWithProperType_validates() throws ValidationException {
    /* Given */
    when(mColumnMethodMock.isGetter()).thenReturn(true);
    when(mColumnMethodMock.isPrimary()).thenReturn(true);

    /* When */
    mFindMethodValidator.validate(mRepositoryMethodMock);

    /* Then */
    // We're happy
  }

  @Test
  public void aRepositoryClassWithAFindMethod_forATableWithPrimaryKeySetterWithProperType_validates() throws ValidationException {
    /* Given */
    when(mColumnMethodMock.isPrimary()).thenReturn(true);

    /* When */
    mFindMethodValidator.validate(mRepositoryMethodMock);

    /* Then */
    // We're happy
  }
}