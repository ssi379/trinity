package com.nhaarman.trinity.internal.codegen.table.repository.validator;

import com.nhaarman.trinity.annotations.Repository;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class RepositoryTypeValidator {

  private final Messager mMessager;

  public RepositoryTypeValidator(final Messager messager) {
    mMessager = messager;
  }

  public boolean validate(final Set<? extends Element> elements) {
    for (Element element : elements) {
      if (!(element instanceof TypeElement)) {
        printInvalidElementMessage(element);
        return false;
      }
    }

    return true;
  }

  private void printInvalidElementMessage(final Element element) {
    AnnotationMirror repositoryAnnotationMirror = getRepositoryAnnotationMirror(element);
    mMessager.printMessage(Diagnostic.Kind.ERROR,
        "@Repository annotation can only be applied to classes.", element,
        repositoryAnnotationMirror);
  }

  private AnnotationMirror getRepositoryAnnotationMirror(final Element element) {
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
