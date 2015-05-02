package com.nhaarman.trinity.internal.codegen.data;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class ParameterTest {

  private static final String FULLY_QUALIFIED_TYPE = "java.lang.Long";
  private static final String VARIABLE_NAME = "name";

  @Test
  public void createdInstanceReturnsProperFullyQualifiedType() {
    /* Given */
    Parameter parameter = new Parameter(FULLY_QUALIFIED_TYPE, VARIABLE_NAME);

    /* When */
    String fullyQualifiedType = parameter.getFullyQualifiedType();

    /* Then */
    assertThat(fullyQualifiedType, is(equalTo(FULLY_QUALIFIED_TYPE)));
  }

  @Test
  public void createdInstanceReturnsProperVariableName() {
    /* Given */
    Parameter parameter = new Parameter(FULLY_QUALIFIED_TYPE, VARIABLE_NAME);

    /* When */
    String variableName = parameter.getVariableName();

    /* Then */
    assertThat(variableName, is(equalTo(VARIABLE_NAME)));
  }
}