package com.nhaarman.trinity.internal.codegen.validator;

import com.nhaarman.trinity.internal.codegen.Message;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ValidationHandler {

  void onError(@NotNull final Element element, @Nullable final AnnotationMirror annotationMirror, @NotNull final Message message, @NotNull final String... args);

  void warn(@NotNull final Message message, @NotNull final Element element, @Nullable final AnnotationMirror annotationMirror, @NotNull final String... args);
}
