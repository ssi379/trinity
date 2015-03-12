/*
 * Copyright 2015 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nhaarman.trinity.internal.codegen.validator;

import com.nhaarman.trinity.internal.codegen.ProcessingException;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class TableClassValidator implements Validator<Collection<? extends TableClass>> {

  @Override
  public void validate(@NotNull final Collection<? extends TableClass> tableClasses) throws ProcessingException {
    validateTableNames(tableClasses);
  }

  private void validateTableNames(@NotNull final Collection<? extends TableClass> tableClasses) throws ProcessingException {
    Set<String> tableNames = new HashSet<>();
    for (TableClass tableClass : tableClasses) {
      if (tableNames.contains(tableClass.getTableName())) {
        throwProcessingException(tableClass);
      }

      tableNames.add(tableClass.getTableName());
    }
  }

  private void throwProcessingException(@NotNull final TableClass tableClass) throws ProcessingException {
    throw new ProcessingException("Cannot create two tables with the same name", tableClass.getElement());
  }
}
