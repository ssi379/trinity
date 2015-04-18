package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import java.io.IOException;
import javax.annotation.processing.RoundEnvironment;
import org.jetbrains.annotations.NotNull;

public interface ProcessingStep {

  @NotNull
  ProcessingStepResult process(@NotNull final RoundEnvironment roundEnvironment) throws IOException;
}
