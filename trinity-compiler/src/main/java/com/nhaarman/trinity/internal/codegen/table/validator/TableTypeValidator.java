package com.nhaarman.trinity.internal.codegen.table.validator;

import com.nhaarman.trinity.annotations.Table;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.tools.Diagnostic;

/**
 * A class which validates the elements annotated with the @{@link Table} annotation. <p/> Table
 * elements should be classes.
 */
public class TableTypeValidator {

  private final Messager mMessager;

  public TableTypeValidator(final Messager messager) {
    mMessager = messager;
  }

  public boolean validate(final Set<? extends Element> elements) {
    boolean result = true;

    for (Element element : elements) {
      if (element.getKind() != ElementKind.CLASS) {
        printInvalidElementMessage(element);
        result = false;
      }
    }

    return result;
  }

  private void printInvalidElementMessage(final Element element) {
    AnnotationMirror tableAnnotationMirror = getTableAnnotationMirror(element);
    mMessager.printMessage(Diagnostic.Kind.ERROR,
        "@Table annotation can only be applied to classes.", element, tableAnnotationMirror);
  }

  private AnnotationMirror getTableAnnotationMirror(final Element element) {
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
