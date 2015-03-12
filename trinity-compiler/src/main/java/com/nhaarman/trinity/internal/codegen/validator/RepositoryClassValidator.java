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

import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import java.util.Collection;
import javax.annotation.processing.Messager;
import org.jetbrains.annotations.NotNull;

public class RepositoryClassValidator {

  public boolean validate(@NotNull final Collection<RepositoryClass> repositoryClasses) {
    boolean result = true;

    for (RepositoryClass repositoryClass : repositoryClasses) {
      result &= validate(repositoryClass);
    }

    return result;
  }

  private boolean validate(@NotNull final RepositoryClass repositoryClass) {
    return true;
  }
}
