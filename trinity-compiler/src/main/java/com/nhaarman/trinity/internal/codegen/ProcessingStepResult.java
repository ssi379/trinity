package com.nhaarman.trinity.internal.codegen;

import org.jetbrains.annotations.NotNull;

public enum ProcessingStepResult {

  OK, WARNING, ERROR;

  public ProcessingStepResult and(@NotNull final ProcessingStepResult other) {
    if (this == ERROR || other == ERROR) {
      return ERROR;
    } else if (this == WARNING || other == WARNING) {
      return WARNING;
    } else {
      return OK;
    }
  }
}
