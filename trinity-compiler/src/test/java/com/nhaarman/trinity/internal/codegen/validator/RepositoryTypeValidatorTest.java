package com.nhaarman.trinity.internal.codegen.validator;

import com.nhaarman.trinity.internal.codegen.ValidationException;
import java.util.HashSet;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class RepositoryTypeValidatorTest {

  private RepositoryTypeValidator mRepositoryTypeValidator;

  @Before
  public void setUp() {
    mRepositoryTypeValidator = new RepositoryTypeValidator();
  }

  @Test
  public void validatingOnlyTypeElements_validatesSuccessfully() throws ValidationException {
    /* Given */
    Set<Element> elements = new HashSet<>();
    for (int i = 0; i < 5; i++) {
      elements.add(mock(TypeElement.class));
    }

    /* When */
    mRepositoryTypeValidator.validate(elements);

    /* Then */
    // We're happy
  }

  @Test(expected = ValidationException.class)
  public void validatingANonTypeElement_throwsAnException() throws ValidationException {
    /* Given */
    Set<Element> elements = new HashSet<>();
    for (int i = 0; i < 5; i++) {
      elements.add(mock(Element.class));
    }

    /* When */
    mRepositoryTypeValidator.validate(elements);

    /* Then */
    // We're happy
  }
}