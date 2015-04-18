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

import com.nhaarman.trinity.internal.codegen.Message;
import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.ERROR;
import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.OK;

public class TableClassValidator implements Validator<Collection<? extends TableClass>> {

  @NotNull
  @Override
  public ProcessingStepResult validate(@NotNull final Collection<? extends TableClass> tableClasses, @NotNull final ValidationHandler validationHandler) {
    return validateTableNames(tableClasses, validationHandler);
  }

  @NotNull
  private ProcessingStepResult validateTableNames(@NotNull final Collection<? extends TableClass> tableClasses, @NotNull final ValidationHandler validationHandler) {
    ProcessingStepResult result = OK;

    Set<String> tableNames = new HashSet<>();
    for (TableClass tableClass : tableClasses) {
      if (tableNames.contains(tableClass.getTableName())) {
        validationHandler.onError(
            tableClass.getElement(),
            null,
            Message.CANNOT_CREATE_TWO_TABLES_WITH_THE_SAME_NAME,
            tableClass.getTableName()
        );
        result = ERROR;
      }

      tableNames.add(tableClass.getTableName());
    }

    return result;
  }
}
