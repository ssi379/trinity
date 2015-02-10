package com.nhaarman.trinity.internal.codegen.validator;

import com.nhaarman.trinity.internal.codegen.ProcessingException;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class TableClassValidator implements Validator<Set<? extends TableClass>> {

  @Override
  public void validate(@NotNull final Set<? extends TableClass> tableClasses) throws ProcessingException {
    validateTableNames(tableClasses);
  }

  private void validateTableNames(@NotNull final Set<? extends TableClass> tableClasses) throws ProcessingException {
    Set<String> tableNames = new HashSet<>();
    for (TableClass tableClass : tableClasses) {
      if (tableNames.contains(tableClass.getTableName())) {
        throwProcessingException(tableClass);
      }

      tableNames.add(tableClass.getTableName());
    }
  }

  private void throwProcessingException(@NotNull final TableClass tableClass) throws ProcessingException {
    throw new ProcessingException("Cannot create two tables with the same name", tableClass.getTypeElement(), tableClass.getTableAnnotationMirror());
  }
}
