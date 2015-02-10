package com.nhaarman.trinity.internal.codegen;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProcessingException extends Exception {

  @NotNull
  private final Element mElement;

  @Nullable
  private final AnnotationMirror mAnnotationMirror;

  public ProcessingException(@NotNull final String message,
                             @NotNull final Element element) {
    this(message, element, null);
  }

  public ProcessingException(@NotNull final String message,
                             @NotNull final Element element,
                             @Nullable final AnnotationMirror annotationMirror) {
    super(message);
    mElement = element;
    mAnnotationMirror = annotationMirror;
  }

  @NotNull
  public Element getElement() {
    return mElement;
  }

  @Nullable
  public AnnotationMirror getAnnotationMirror() {
    return mAnnotationMirror;
  }
}
