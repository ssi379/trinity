package com.nhaarman.trinity.internal.codegen.validator;

import com.nhaarman.trinity.internal.codegen.data.TableClass;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

public class TableClassValidator {

  private final Messager mMessager;

  public TableClassValidator(final Messager messager) {
    mMessager = messager;
  }

  public boolean validate(final Collection<TableClass> tableClasses) {
    if (!validateTableNames(tableClasses)) {
      return false;
    }

    return true;
  }

  private boolean validateTableNames(final Collection<TableClass> tableClasses) {
    Set<String> tableNames = new HashSet<>();
    for (TableClass tableClass : tableClasses) {
      if (tableNames.contains(tableClass.getTableName())) {
        printDuplicateTableDeclarationMessage(tableClass);
        return false;
      }

      tableNames.add(tableClass.getTableName());
    }
    return true;
  }

  private void printDuplicateTableDeclarationMessage(final TableClass tableClass) {
    mMessager.printMessage(
        Diagnostic.Kind.ERROR,
        "Cannot create two tables with the same name",
        tableClass.getTypeElement(),
        tableClass.getTableAnnotationMirror()
    );
  }
}
