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
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
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
    Collection<RepositoryMethod> methods = repositoryClass.getMethods();
    for (RepositoryMethod method : methods) {
      if (method.getMethodName().equals("find") || method.getMethodName().equals("findById")) {
        validateFindMethod(repositoryClass, method);
      }
    }
  }

  private void validateFindMethod(@NotNull final RepositoryClass repositoryClass, @NotNull final RepositoryMethod method) throws ValidationException {
    ColumnMethod primaryKeyGetter = mColumnMethodRepository.findPrimaryKeyGetter(repositoryClass.getTableClassFullyQualifiedName());
    ColumnMethod primaryKeySetter = mColumnMethodRepository.findPrimaryKeySetter(repositoryClass.getTableClassFullyQualifiedName());

    if (primaryKeyGetter == null && primaryKeySetter == null) {
      throw new ValidationException(String.format("Generating a '%s' implementation requires at least one method in %s to be annotated with @PrimaryKey.", method.getMethodName(),
          repositoryClass.getTableClassFullyQualifiedName()), method.getElement());
    }

    if (primaryKeyGetter != null && !primaryKeyGetter.getType().equals(method.getParameter().getType())) {
      throw new ValidationException(
          String.format(
              "Type '%s' of method '%s' does not match with type '%s' of method '%s.%s'",
              method.getParameter().getType(),
              method.getMethodName(),
              primaryKeyGetter.getType(),
              primaryKeyGetter.getFullyQualifiedTableClassName(),
              primaryKeyGetter.getMethodName()
          ),
          method.getElement()
      );
    }

    if (primaryKeySetter != null && !primaryKeySetter.getType().equals(method.getParameter().getType())) {
      throw new ValidationException(
          String.format(
              "Type '%s' of method '%s' does not match with type '%s' of method '%s.%s'",
              method.getParameter().getType(),
              method.getMethodName(),
              primaryKeySetter.getType(),
              primaryKeySetter.getFullyQualifiedTableClassName(),
              primaryKeySetter.getMethodName()
          ),
          method.getElement()
      );
    }
  }
}
