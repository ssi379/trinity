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

import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.method.CreateMethod;
import com.nhaarman.trinity.internal.codegen.method.FindAllMethod;
import com.nhaarman.trinity.internal.codegen.method.FindMethod;
import com.nhaarman.trinity.internal.codegen.method.MethodVisitor;
import com.nhaarman.trinity.internal.codegen.method.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.validator.method.MethodValidatorFactory;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.OK;

public class RepositoryClassValidator implements Validator<Collection<RepositoryClass>>, MethodVisitor {

  @NotNull
  private final ColumnMethodRepository mColumnMethodRepository;

  private ProcessingStepResult mResult;
  private MethodValidatorFactory mMethodValidatorFactory;
  private ValidationHandler mValidationHandler;

  public RepositoryClassValidator(@NotNull final ColumnMethodRepository columnMethodRepository) {
    mColumnMethodRepository = columnMethodRepository;
  }

  @NotNull
  @Override
  public ProcessingStepResult validate(@NotNull final Collection<RepositoryClass> repositoryClasses, @NotNull final ValidationHandler validationHandler) {
    mValidationHandler = validationHandler;
    mResult = OK;

    for (RepositoryClass repositoryClass : repositoryClasses) {
      validate(repositoryClass);
    }

    return mResult;
  }

  private void validate(@NotNull final RepositoryClass repositoryClass) {
    mMethodValidatorFactory = new MethodValidatorFactory(mColumnMethodRepository, repositoryClass);

    Collection<RepositoryMethod> methods = repositoryClass.getMethods();
    for (RepositoryMethod method : methods) {
      method.accept(this);
    }
  }

  @Override
  public void visit(@NotNull final FindMethod findMethod) {
    mResult = mResult.and(mMethodValidatorFactory.findMethodValidator().validate(findMethod, mValidationHandler));
  }

  @Override
  public void visit(@NotNull final CreateMethod createMethod) {
    mResult = mResult.and(mMethodValidatorFactory.createMethodValidator().validate(createMethod, mValidationHandler));
  }

  @Override
  public void visit(@NotNull final FindAllMethod findAllMethod) {
    mResult = mResult.and(mMethodValidatorFactory.findAllMethodValidator().validate(findAllMethod, mValidationHandler));
  }
}
