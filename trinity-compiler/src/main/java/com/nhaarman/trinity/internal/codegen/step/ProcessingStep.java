package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.internal.codegen.ProcessingException;
import java.io.IOException;
import javax.annotation.processing.RoundEnvironment;
import org.jetbrains.annotations.NotNull;

public interface ProcessingStep {

  void process(@NotNull final RoundEnvironment roundEnvironment) throws ProcessingException, IOException;
}
