package com.nhaarman.trinity.internal.codegen.column.validator;

import com.nhaarman.trinity.internal.codegen.column.ColumnInfo;
import com.nhaarman.trinity.internal.codegen.column.ColumnMethodInfo;

import java.util.Collection;
import java.util.Iterator;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

public class ColumnValidator {

    private final Messager mMessager;

    public ColumnValidator(final Messager messager) {
        mMessager = messager;
    }

    public boolean validate(final Collection<ColumnInfo> columnInfos) {
        for (ColumnInfo columnInfo : columnInfos) {
            Collection<ColumnMethodInfo> methods = columnInfo.getMethodInfos();
            Iterator<ColumnMethodInfo> iterator = methods.iterator();
            ColumnMethodInfo baseInfo = iterator.next();
            while (iterator.hasNext()) {
                ColumnMethodInfo info2 = iterator.next();
                if (!info2.getType().equals(baseInfo.getType())) {
                    mMessager.printMessage(
                            Diagnostic.Kind.ERROR,
                            "Methods annotated with the same column name should have the same type.\n" +
                                    "\tFound:\n" +
                                    "\t\t- " + baseInfo.getType() + " in " + baseInfo.getExecutableElement().getSimpleName() + "\n" +
                                    "\t\t- " + info2.getType() + " in " + info2.getExecutableElement().getSimpleName() + "\n",
                            info2.getExecutableElement(),
                            info2.getColumnAnnotationMirror()
                    );
                    return false;
                }
            }
        }
        return true;
    }
}
