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

import com.nhaarman.trinity.internal.codegen.ValidationException;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.validator.method.MethodValidatorFactory;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;

public class RepositoryClassValidator implements Validator<Collection<RepositoryClass>> {

  @NotNull
  private final ColumnMethodRepository mColumnMethodRepository;

  public RepositoryClassValidator(@NotNull final ColumnMethodRepository columnMethodRepository) {
    mColumnMethodRepository = columnMethodRepository;
  }

  @Override
  public void validate(@NotNull final Collection<RepositoryClass> repositoryClasses) throws ValidationException {
    for (RepositoryClass repositoryClass : repositoryClasses) {
      validate(repositoryClass);
    }
  }

  private void validate(@NotNull final RepositoryClass repositoryClass) throws ValidationException {
    MethodValidatorFactory methodValidatorFactory = new MethodValidatorFactory(mColumnMethodRepository, repositoryClass);

    Collection<RepositoryMethod> methods = repositoryClass.getMethods();
    for (RepositoryMethod method : methods) {
      methodValidatorFactory.validatorFor(method).validate(method);
    }
  }
}
