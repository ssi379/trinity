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

import com.nhaarman.trinity.annotations.Column;
import com.nhaarman.trinity.annotations.Serializer;
import com.nhaarman.trinity.internal.codegen.Message;
import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.ERROR;
import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.OK;

public class SerializerTypeValidator implements Validator<Set<? extends Element>> {

  @NotNull
  @Override
  public ProcessingStepResult validate(@NotNull final Set<? extends Element> elements, @NotNull final ValidationHandler validationHandler) {
    ProcessingStepResult result = OK;

    for (Element element : elements) {
      result = result.and(validate(element, validationHandler));
    }

    return result;
  }

  @NotNull
  private ProcessingStepResult validate(@NotNull final Element element, @NotNull final ValidationHandler validationHandler) {
    return validateElementKind(element, validationHandler)
        .and(validateElementParent((TypeElement) element, validationHandler));
  }

  @NotNull
  private ProcessingStepResult validateElementKind(@NotNull final Element element, @NotNull final ValidationHandler validationHandler) {
    if (element.getKind() != ElementKind.CLASS) {
      AnnotationMirror serializerAnnotationMirror = getSerializerAnnotationMirror(element);
      validationHandler.onError(element, serializerAnnotationMirror, Message.SERIALIZER_ANNOTATION_CAN_ONLY_BE_APPLIED_TO_CLASSES_IMPLEMENTING_SERIALIZER);
      return ERROR;
    }

    return OK;
  }

  @NotNull
  private ProcessingStepResult validateElementParent(@NotNull final TypeElement element, @NotNull final ValidationHandler validationHandler) {
    List<? extends TypeMirror> interfaces = element.getInterfaces();
    if(!interfaces.isEmpty()){
      for (TypeMirror anInterface : interfaces) {
        if(anInterface.toString().startsWith("com.nhaarman.trinity.TypeSerializer")){
          return OK;
        }
      }
    }

    AnnotationMirror serializerAnnotationMirror = getSerializerAnnotationMirror(element);
    validationHandler.onError(element, serializerAnnotationMirror, Message.SERIALIZER_ANNOTATION_CAN_ONLY_BE_APPLIED_TO_CLASSES_IMPLEMENTING_SERIALIZER);
    return ERROR;
  }

  /**
   * Returns the {@link Column} AnnotationMirror on given Element.
   *
   * @return null if the AnnotationMirror is not found.
   */
  private AnnotationMirror getSerializerAnnotationMirror(@NotNull final Element element) {
    List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
    AnnotationMirror serializerAnnotationMirror = null;
    for (AnnotationMirror annotationMirror : annotationMirrors) {
      if (annotationMirror.getAnnotationType().toString().equals(Serializer.class.getCanonicalName())) {
        serializerAnnotationMirror = annotationMirror;
      }
    }
    return serializerAnnotationMirror;
  }
}
