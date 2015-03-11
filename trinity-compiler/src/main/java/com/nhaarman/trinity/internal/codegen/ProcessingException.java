/*
 * Copyright 2015 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nhaarman.trinity.internal.codegen;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProcessingException extends Exception {

  @NotNull
  private final transient Element mElement;

  @Nullable
  private final transient AnnotationMirror mAnnotationMirror;

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
