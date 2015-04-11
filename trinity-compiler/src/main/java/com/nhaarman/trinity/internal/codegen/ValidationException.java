package com.nhaarman.trinity.internal.codegen;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ValidationException extends ProcessingException {

  public ValidationException(@NotNull final String message, @NotNull final Element element) {
    super(message, element);
  }

  public ValidationException(@NotNull final String message, @NotNull final Element element, @Nullable final AnnotationMirror annotationMirror) {
    super(message, element, annotationMirror);
  }
}
