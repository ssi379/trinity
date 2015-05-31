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
import com.nhaarman.trinity.internal.codegen.step.SerializerClassConversionStep;
import com.nhaarman.trinity.internal.codegen.step.TableClassConversionStep;
import com.nhaarman.trinity.internal.codegen.step.TableClassValidationStep;
import com.nhaarman.trinity.internal.codegen.step.ValidateColumnElementsStep;
import com.nhaarman.trinity.internal.codegen.step.ValidateRepositoryElementsStep;
import com.nhaarman.trinity.internal.codegen.step.ValidateSerializerElementsStep;
import com.nhaarman.trinity.internal.codegen.step.ValidateTableElementsStep;
import com.nhaarman.trinity.internal.codegen.validator.ValidationHandler;
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
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.ERROR;
import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.OK;

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

    ValidationHandler validationHandler = new MyValidationHandler(mMessager);

    mSteps.addAll(
        Arrays.asList(
            new ValidateSerializerElementsStep(validationHandler),
            new ValidateTableElementsStep(validationHandler),
            new ValidateColumnElementsStep(validationHandler),
            new ValidateRepositoryElementsStep(validationHandler),

            new SerializerClassConversionStep(mRepositoryGateway.getSerializerClassRepository()),

            new TableClassConversionStep(mRepositoryGateway.getTableClassRepository()),
            new TableClassValidationStep(mRepositoryGateway.getTableClassRepository(), validationHandler),

            new ColumnMethodConversionStep(mRepositoryGateway.getColumnMethodRepository()),
            new ColumnMethodValidationStep(
                mRepositoryGateway.getTableClassRepository(),
                mRepositoryGateway.getColumnMethodRepository(),
                mRepositoryGateway.getSerializerClassRepository(),
                validationHandler
            ),

            new RepositoryClassConversionStep(mRepositoryGateway.getRepositoryClassRepository()),
            new RepositoryClassValidationStep(
                mRepositoryGateway.getRepositoryClassRepository(),
                mRepositoryGateway.getColumnMethodRepository(),
                validationHandler
            ),

            new RepositoryWriterStep(
                mRepositoryGateway.getTableClassRepository(),
                mRepositoryGateway.getRepositoryClassRepository(),
                mRepositoryGateway.getColumnMethodRepository(),
                mRepositoryGateway.getSerializerClassRepository(),
                processingEnv.getFiler()
            )
        )
    );
  }

  @Override
  public boolean process(@NotNull final Set<? extends TypeElement> annotations,
                         @NotNull final RoundEnvironment roundEnv) {
    mRepositoryGateway.getSerializerClassRepository().clear();
    mRepositoryGateway.getTableClassRepository().clear();
    mRepositoryGateway.getColumnMethodRepository().clear();
    mRepositoryGateway.getRepositoryClassRepository().clear();

    try {
      ProcessingStepResult result = OK;
      for (int i = 0; i < mSteps.size() && result != ERROR; i++) {
        result = result.and(mSteps.get(i).process(roundEnv));
      }
    } catch (IOException e) {
      mMessager.printMessage(Kind.ERROR, e.getLocalizedMessage());
    }

    return true;
  }

  private static class MyValidationHandler implements ValidationHandler {

    @NotNull
    private final Messager mMessager;

    MyValidationHandler(@NotNull final Messager messager) {
      mMessager = messager;
    }

    @Override
    public void onError(@NotNull final Element element,
                        @Nullable final AnnotationMirror annotationMirror,
                        @NotNull final Message message,
                        @NotNull final String... args) {
      mMessager.printMessage(Kind.ERROR, message.toLocalizedString(args), element, annotationMirror);
    }

    @Override
    public void warn(@NotNull final Message message, @NotNull final Element element, @Nullable final AnnotationMirror annotationMirror, @NotNull final String... args) {
      mMessager.printMessage(Kind.WARNING, message.toLocalizedString(args), element, annotationMirror);
    }
  }
}