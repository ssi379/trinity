package com.nhaarman.trinity.internal.codegen;

import org.junit.Test;

import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.ERROR;
import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.OK;
import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.WARNING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ProcessingStepResultTest {

  @Test
  public void ok_and_ok_equals_ok() {
    /* When */
    ProcessingStepResult result = OK.and(OK);

    /* Then */
    assertThat(result, is(OK));
  }

  @Test
  public void ok_and_warning_equals_warning() {
    /* When */
    ProcessingStepResult result = OK.and(WARNING);

    /* Then */
    assertThat(result, is(WARNING));
  }

  @Test
  public void ok_and_error_equals_error() {
    /* When */
    ProcessingStepResult result = OK.and(ERROR);

    /* Then */
    assertThat(result, is(ERROR));
  }

  @Test
  public void warning_and_ok_equals_warning() {
    /* When */
    ProcessingStepResult result = WARNING.and(OK);

    /* Then */
    assertThat(result, is(WARNING));
  }

  @Test
  public void warning_and_warning_equals_warning() {
    /* When */
    ProcessingStepResult result = WARNING.and(WARNING);

    /* Then */
    assertThat(result, is(WARNING));
  }

  @Test
  public void warning_and_error_equals_error() {
    /* When */
    ProcessingStepResult result = WARNING.and(ERROR);

    /* Then */
    assertThat(result, is(ERROR));
  }

  @Test
  public void error_and_ok_equals_error() {
    /* When */
    ProcessingStepResult result = ERROR.and(OK);

    /* Then */
    assertThat(result, is(ERROR));
  }

  @Test
  public void error_and_warning_equals_error() {
    /* When */
    ProcessingStepResult result = ERROR.and(WARNING);

    /* Then */
    assertThat(result, is(ERROR));
  }

  @Test
  public void error_and_error_equals_error() {
    /* When */
    ProcessingStepResult result = ERROR.and(ERROR);

    /* Then */
    assertThat(result, is(ERROR));
  }

}