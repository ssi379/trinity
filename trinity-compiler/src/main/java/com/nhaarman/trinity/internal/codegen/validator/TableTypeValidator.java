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

package com.nhaarman.trinity.internal.codegen.validator;

import com.nhaarman.trinity.annotations.Table;
import com.nhaarman.trinity.internal.codegen.ValidationException;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import org.jetbrains.annotations.NotNull;

/**
 * A class which validates the elements annotated with the @{@link Table} annotation.
 *
 * Table elements should be classes.
 */
public class TableTypeValidator implements Validator<Set<? extends Element>> {

  @Override
  public void validate(@NotNull final Set<? extends Element> elements) throws ValidationException {
    for (Element element : elements) {
      if (element.getKind() != ElementKind.CLASS) {
        throwProcessingException(element);
      }
    }
  }

  private void throwProcessingException(@NotNull final Element element) throws ValidationException {
    AnnotationMirror tableAnnotationMirror = getTableAnnotationMirror(element);
    throw new ValidationException("@Table annotation can only be applied to classes.", element, tableAnnotationMirror);
  }

  private AnnotationMirror getTableAnnotationMirror(@NotNull final Element element) {
    List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
    AnnotationMirror tableAnnotationMirror = null;
    for (AnnotationMirror annotationMirror : annotationMirrors) {
      if (annotationMirror.getAnnotationType().toString().equals(Table.class.getCanonicalName())) {
        tableAnnotationMirror = annotationMirror;
      }
    }
    return tableAnnotationMirror;
  }
}
