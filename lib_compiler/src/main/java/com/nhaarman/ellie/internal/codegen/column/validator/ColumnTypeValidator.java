package com.nhaarman.ellie.internal.codegen.column.validator;

import java.util.Set;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;

public class ColumnTypeValidator {

    private final Messager mMessager;

    public ColumnTypeValidator(final Messager messager) {
        mMessager = messager;
    }

    public boolean validates(final Set<? extends Element> columnElements) {
return true;
    }
}
