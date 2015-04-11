package com.nhaarman.trinity.internal.codegen.validator;

import com.nhaarman.trinity.internal.codegen.ValidationException;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.Parameter;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import java.util.Arrays;
import java.util.Collection;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import org.junit.Before;
import org.junit.Test;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;

public class RepositoryClassValidatorTest {

  private RepositoryClassValidator mRepositoryClassValidator;
  private ColumnMethodRepository mColumnMethodRepository;

  private static RepositoryClass.Builder fullRepositoryClassBuilder(final Collection<RepositoryMethod> repositoryMethods) {
    return new RepositoryClass.Builder()
        .withClassName("ClassName")
        .withPackageName("PackageName")
        .withTableClassName("TableClassName")
        .withTableClassPackageName("TableClassPackageName")
        .withElement(mock(Element.class))
        .withMethods(repositoryMethods);
  }

  private static ColumnMethod.Builder fullColumnMethodBuilder() {
    return new ColumnMethod.Builder()
        .withName("Name")
        .withColumnName("ColumnName")
        .withFullyQualifiedTableClassName("TableClassPackageName.TableClassName")
        .withType(mock(TypeMirror.class))
        .withElement(mock(Element.class));
  }

  private static RepositoryMethod.Builder fullRepositoryMethodBuilder(final Parameter... parameters) {
    return new RepositoryMethod.Builder()
        .withType(mock(TypeMirror.class))
        .withName("find")
        .withParameters(Arrays.asList(parameters))
        .withElement(mock(Element.class));
  }

  @Before
  public void setUp() {
    mColumnMethodRepository = new ColumnMethodRepository();
    mRepositoryClassValidator = new RepositoryClassValidator(mColumnMethodRepository);
  }

  @Test(expected = ValidationException.class)
  public void aRepositoryClassWithAFindMethod_forATableWithoutAPrimaryKey_throwsAValidationException() throws ValidationException {
    /* Given */
    RepositoryMethod repositoryMethod = fullRepositoryMethodBuilder().build();
    RepositoryClass repositoryClass = fullRepositoryClassBuilder(singletonList(repositoryMethod)).build();

    /* When */
    mRepositoryClassValidator.validate(Arrays.asList(repositoryClass));

    /* Then */
    // An exception is thrown.
  }

  @Test(expected = ValidationException.class)
  public void aRepositoryClassWithAFindMethod_forATableWithPrimaryKeyGetterWithDifferentType_throwsAnException() throws ValidationException {
    /* Given */
    TypeMirror columnMethodType = mock(TypeMirror.class);
    when(columnMethodType.toString()).thenReturn(Long.class.getName());

    Parameter parameter = mock(Parameter.class);
    when(parameter.getType()).thenReturn(String.class.getName());

    RepositoryMethod repositoryMethod = fullRepositoryMethodBuilder(parameter).build();
    RepositoryClass repositoryClass = fullRepositoryClassBuilder(singletonList(repositoryMethod)).build();
    ColumnMethod columnMethod = fullColumnMethodBuilder()
        .isPrimary()
        .isGetter()
        .withType(columnMethodType)
        .build();

    mColumnMethodRepository.save(columnMethod);

    /* When */
    mRepositoryClassValidator.validate(Arrays.asList(repositoryClass));

    /* Then */
    // We're happy
  }

  @Test(expected = ValidationException.class)
  public void aRepositoryClassWithAFindMethod_forATableWithPrimaryKeySetterWithDifferentType_throwsAnException() throws ValidationException {
    /* Given */
    TypeMirror columnMethodType = mock(TypeMirror.class);
    when(columnMethodType.toString()).thenReturn(Long.class.getName());

    Parameter parameter = mock(Parameter.class);
    when(parameter.getType()).thenReturn(String.class.getName());

    RepositoryMethod repositoryMethod = fullRepositoryMethodBuilder(parameter).build();
    RepositoryClass repositoryClass = fullRepositoryClassBuilder(singletonList(repositoryMethod)).build();
    ColumnMethod columnMethod = fullColumnMethodBuilder()
        .isPrimary()
        .isSetter()
        .withType(columnMethodType)
        .build();

    mColumnMethodRepository.save(columnMethod);

    /* When */
    mRepositoryClassValidator.validate(Arrays.asList(repositoryClass));

    /* Then */
    // We're happy
  }

  @Test
  public void aRepositoryClassWithAFindMethod_forATableWithPrimaryKeyGetterWithProperType_validates() throws ValidationException {
    /* Given */
    TypeMirror columnMethodType = mock(TypeMirror.class);
    when(columnMethodType.toString()).thenReturn(String.class.getName());

    Parameter parameter = mock(Parameter.class);
    when(parameter.getType()).thenReturn(String.class.getName());

    RepositoryMethod repositoryMethod = fullRepositoryMethodBuilder(parameter).build();
    RepositoryClass repositoryClass = fullRepositoryClassBuilder(singletonList(repositoryMethod)).build();
    ColumnMethod columnMethod = fullColumnMethodBuilder()
        .isPrimary()
        .isGetter()
        .withType(columnMethodType)
        .build();

    mColumnMethodRepository.save(columnMethod);

    /* When */
    mRepositoryClassValidator.validate(Arrays.asList(repositoryClass));

    /* Then */
    // We're happy
  }

  @Test
  public void aRepositoryClassWithAFindMethod_forATableWithPrimaryKeySetterWithProperType_validates() throws ValidationException {
    /* Given */
    TypeMirror columnMethodType = mock(TypeMirror.class);
    when(columnMethodType.toString()).thenReturn(String.class.getName());

    Parameter parameter = mock(Parameter.class);
    when(parameter.getType()).thenReturn(String.class.getName());

    RepositoryMethod repositoryMethod = fullRepositoryMethodBuilder(parameter).build();
    RepositoryClass repositoryClass = fullRepositoryClassBuilder(singletonList(repositoryMethod)).build();
    ColumnMethod columnMethod = fullColumnMethodBuilder()
        .isPrimary()
        .isSetter()
        .withType(columnMethodType)
        .build();

    mColumnMethodRepository.save(columnMethod);

    /* When */
    mRepositoryClassValidator.validate(Arrays.asList(repositoryClass));

    /* Then */
    // We're happy
  }
}