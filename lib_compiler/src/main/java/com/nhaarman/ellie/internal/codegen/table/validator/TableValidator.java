package com.nhaarman.ellie.internal.codegen.table.validator;

import com.nhaarman.ellie.internal.codegen.table.TableInfo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

public class TableValidator {

    private final Messager mMessager;

    public TableValidator(final Messager messager) {
        mMessager = messager;
    }

    public boolean validates(final Collection<TableInfo> tableInfos) {
        if (!validateTableNames(tableInfos)) {
            return false;
        }

        return true;
    }

    private boolean validateTableNames(final Collection<TableInfo> tableInfos) {
        Set<String> tableNames = new HashSet<>();
        for (TableInfo tableInfo : tableInfos) {
            if (tableNames.contains(tableInfo.getTableName())) {
                printDuplicateTableDeclarationMessage(tableInfo);
                return false;
            }

            tableNames.add(tableInfo.getTableName());
        }
        return true;
    }

    private void printDuplicateTableDeclarationMessage(final TableInfo tableInfo) {
        mMessager.printMessage(
                Diagnostic.Kind.ERROR,
                "Cannot create two tables with the same name",
                tableInfo.getElement(),
                tableInfo.getTableAnnotationMirror()
        );
    }
}
