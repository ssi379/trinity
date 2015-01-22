package com.nhaarman.trinity.internal.codegen.table.column.validator;

import java.util.Set;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;

public class ColumnTypeValidator {

    private final Messager mMessager;

    public ColumnTypeValidator(final Messager messager) {
        mMessager = messager;
    }

    public boolean validate(final Set<? extends Element> columnElements) {
return true;
    }
}
