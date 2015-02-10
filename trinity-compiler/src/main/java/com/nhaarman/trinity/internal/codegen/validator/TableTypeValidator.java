package com.nhaarman.trinity.internal.codegen.validator;

import com.nhaarman.trinity.annotations.Table;
import com.nhaarman.trinity.internal.codegen.ProcessingException;
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
  public void validate(@NotNull final Set<? extends Element> elements) throws ProcessingException {
    for (Element element : elements) {
      if (element.getKind() != ElementKind.CLASS) {
        throwProcessingException(element);
      }
    }
  }

  private void throwProcessingException(@NotNull final Element element) throws ProcessingException {
    AnnotationMirror tableAnnotationMirror = getTableAnnotationMirror(element);
    throw new ProcessingException("@Table annotation can only be applied to classes.", element, tableAnnotationMirror);
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
