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
