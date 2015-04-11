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

package com.nhaarman.trinity.internal.codegen;

import com.nhaarman.trinity.internal.codegen.data.RepositoryGateway;
import com.nhaarman.trinity.internal.codegen.step.ColumnMethodConversionStep;
import com.nhaarman.trinity.internal.codegen.step.ColumnMethodValidationStep;
import com.nhaarman.trinity.internal.codegen.step.ProcessingStep;
import com.nhaarman.trinity.internal.codegen.step.RepositoryClassConversionStep;
import com.nhaarman.trinity.internal.codegen.step.RepositoryClassValidationStep;
import com.nhaarman.trinity.internal.codegen.step.RepositoryWriterStep;
import com.nhaarman.trinity.internal.codegen.step.TableClassConversionStep;
import com.nhaarman.trinity.internal.codegen.step.TableClassValidationStep;
import com.nhaarman.trinity.internal.codegen.step.ValidateColumnElementsStep;
import com.nhaarman.trinity.internal.codegen.step.ValidateRepositoryElementsStep;
import com.nhaarman.trinity.internal.codegen.step.ValidateTableElementsStep;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import org.jetbrains.annotations.NotNull;

@SupportedAnnotationTypes("com.nhaarman.trinity.annotations.*")
public class TrinityProcessor extends AbstractProcessor {

  @NotNull
  private final List<ProcessingStep> mSteps = new LinkedList<>();

  @NotNull
  private final RepositoryGateway mRepositoryGateway = new RepositoryGateway();

  private Messager mMessager;

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  public void init(@NotNull final ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    mMessager = processingEnv.getMessager();

    mSteps.addAll(
        Arrays.asList(
            new ValidateTableElementsStep(),
            new ValidateColumnElementsStep(),
            new ValidateRepositoryElementsStep(),

            new TableClassConversionStep(mRepositoryGateway.getTableClassRepository()),
            new TableClassValidationStep(mRepositoryGateway.getTableClassRepository()),

            new ColumnMethodConversionStep(mRepositoryGateway.getColumnMethodRepository()),
            new ColumnMethodValidationStep(mRepositoryGateway.getTableClassRepository(), mRepositoryGateway.getColumnMethodRepository()),

            new RepositoryClassConversionStep(mRepositoryGateway.getRepositoryClassRepository()),
            new RepositoryClassValidationStep(mRepositoryGateway.getRepositoryClassRepository(), mRepositoryGateway.getColumnMethodRepository()),

            new RepositoryWriterStep(
                mRepositoryGateway.getTableClassRepository(),
                mRepositoryGateway.getRepositoryClassRepository(),
                mRepositoryGateway.getColumnMethodRepository(),
                processingEnv.getFiler()
            )
        )
    );
  }

  @Override
  public boolean process(@NotNull final Set<? extends TypeElement> annotations,
                         @NotNull final RoundEnvironment roundEnv) {
    mRepositoryGateway.getTableClassRepository().clear();
    mRepositoryGateway.getColumnMethodRepository().clear();
    mRepositoryGateway.getRepositoryClassRepository().clear();

    try {
      for (ProcessingStep step : mSteps) {
        step.process(roundEnv);
      }
    } catch (ProcessingException e) {
      mMessager.printMessage(Kind.ERROR, e.getLocalizedMessage(), e.getElement(), e.getAnnotationMirror());
    } catch (IOException e) {
      mMessager.printMessage(Kind.ERROR, e.getLocalizedMessage());
    }

    return true;
  }
}