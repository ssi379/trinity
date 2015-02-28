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

import com.nhaarman.trinity.annotations.Repository;
import com.nhaarman.trinity.internal.codegen.ProcessingException;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import org.jetbrains.annotations.NotNull;

public class RepositoryTypeValidator implements Validator<Set<? extends Element>> {

  @Override
  public void validate(@NotNull final Set<? extends Element> elements) throws ProcessingException {
    for (Element element : elements) {
      if (!(element instanceof TypeElement)) {
        throwProcessingException(element);
      }
    }
  }

  private void throwProcessingException(@NotNull final Element element) throws ProcessingException {
    AnnotationMirror repositoryAnnotationMirror = getRepositoryAnnotationMirror(element);
    throw new ProcessingException("@Repository annotation can only be applied to classes.", element, repositoryAnnotationMirror);
  }

  private AnnotationMirror getRepositoryAnnotationMirror(@NotNull final Element element) {
    List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
    AnnotationMirror tableAnnotationMirror = null;
    for (AnnotationMirror annotationMirror : annotationMirrors) {
      if (annotationMirror.getAnnotationType()
          .toString()
          .equals(Repository.class.getCanonicalName())) {
        tableAnnotationMirror = annotationMirror;
      }
    }
    return tableAnnotationMirror;
  }
}
