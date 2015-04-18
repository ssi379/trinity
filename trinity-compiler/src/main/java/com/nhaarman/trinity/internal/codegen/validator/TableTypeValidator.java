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
import com.nhaarman.trinity.internal.codegen.Message;
import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.ERROR;
import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.OK;

/**
 * A class which validates the elements annotated with the @{@link Table} annotation.
 * <p/>
 * The following rules are validated:
 * - Table elements should be classes.
 */
public class TableTypeValidator implements Validator<Set<? extends Element>> {

  @Override
  @NotNull
  public ProcessingStepResult validate(@NotNull final Set<? extends Element> elements, @NotNull final ValidationHandler validationHandler) {
    ProcessingStepResult result = OK;

    for (Element element : elements) {
      result = result.and(validate(element, validationHandler));
    }

    return result;
  }

  @NotNull
  private ProcessingStepResult validate(@NotNull final Element element, @NotNull final ValidationHandler validationHandler) {
    return validateElementKind(element, validationHandler);
  }

  @NotNull
  private ProcessingStepResult validateElementKind(@NotNull final Element element, @NotNull final ValidationHandler validationHandler) {
    if (element.getKind() != ElementKind.CLASS) {
      AnnotationMirror tableAnnotationMirror = getTableAnnotationMirror(element);
      validationHandler.onError(element, tableAnnotationMirror, Message.TABLE_ANNOTATION_CAN_ONLY_BE_APPLIED_TO_CLASSES);
      return ERROR;
    }

    return OK;
  }

  /**
   * Returns the {@link Table} AnnotationMirror on given Element.
   *
   * @return null if the AnnotationMirror is not found.
   */
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
