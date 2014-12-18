package com.nhaarman.ellie.internal.codegen.table.validator;

import com.nhaarman.lib_setup.annotations.Table;

import java.util.List;
import java.util.Set;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class TableTypeValidator {

    private final Messager mMessager;

    public TableTypeValidator(final Messager messager) {
        mMessager = messager;
    }

    public boolean validates(final Set<? extends Element> elements) {
        for (Element element : elements) {
            if (!(element instanceof TypeElement)) {
                printInvalidElementMessage(element);
                return false;
            }
        }

        return true;
    }

    private void printInvalidElementMessage(final Element element) {
        AnnotationMirror tableAnnotationMirror = getTableAnnotationMirror(element);
        mMessager.printMessage(Diagnostic.Kind.ERROR, "@Table annotation can only be applied to classes.", element, tableAnnotationMirror);
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
