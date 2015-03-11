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

import com.nhaarman.trinity.annotations.Column;
import com.nhaarman.trinity.annotations.Repository;
import com.nhaarman.trinity.annotations.Table;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClassFactory;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import com.nhaarman.trinity.internal.codegen.data.TableClassFactory;
import com.nhaarman.trinity.internal.codegen.validator.ColumnTypeValidator;
import com.nhaarman.trinity.internal.codegen.validator.RepositoryTypeValidator;
import com.nhaarman.trinity.internal.codegen.validator.TableClassValidator;
import com.nhaarman.trinity.internal.codegen.validator.TableTypeValidator;
import com.nhaarman.trinity.internal.codegen.validator.Validator;
import com.nhaarman.trinity.internal.codegen.writer.RepositoryTypeSpecCreator;
import com.nhaarman.trinity.internal.codegen.writer.TypeSpecWriter;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import org.jetbrains.annotations.NotNull;

@SupportedAnnotationTypes("com.nhaarman.trinity.annotations.*")
public class TrinityProcessor extends AbstractProcessor {

  private Validator<Set<? extends Element>> mTableTypeValidator;
  private Validator<Set<? extends Element>> mColumnTypeValidator;
  private Validator<Set<? extends Element>> mRepositoryTypeValidator;
  private Validator<Set<? extends TableClass>> mTableClassValidator;

  private Messager mMessager;

  private TypeSpecWriter mTypeSpecWriter;

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  public synchronized void init(@NotNull final ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    mMessager = processingEnv.getMessager();

    mTableTypeValidator = new TableTypeValidator();
    mColumnTypeValidator = new ColumnTypeValidator();
    mRepositoryTypeValidator = new RepositoryTypeValidator();
    mTableClassValidator = new TableClassValidator();

    mTypeSpecWriter = new TypeSpecWriter(processingEnv.getFiler());
  }

  @Override
  public synchronized boolean process(@NotNull final Set<? extends TypeElement> annotations,
                                      final RoundEnvironment roundEnv) {
    try {
      process(roundEnv);
    } catch (ProcessingException e) {
      mMessager.printMessage(Kind.ERROR, e.getLocalizedMessage(), e.getElement(), e.getAnnotationMirror());
    } catch (IOException e) {
      mMessager.printMessage(Kind.ERROR, e.getLocalizedMessage());
      return true;
    }

    return true;
  }

  private void process(@NotNull final RoundEnvironment roundEnv) throws ProcessingException, IOException {
    Set<? extends Element> tableElements = findAndValidateTabelElements(roundEnv);
    Set<? extends Element> columnElements = findAndValidateColumnElements(roundEnv);
    Set<? extends Element> repositoryElements = findAndValidateRepositoryElements(roundEnv);
    Set<? extends TableClass> tableClasses = findAndValidateTableClasses(tableElements);
    Set<? extends RepositoryClass> repositoryClasses = findAndValidateRepositoryClasses(repositoryElements);

    writeRepositoryClasses(repositoryClasses, tableClasses);
  }

  private Set<? extends Element> findAndValidateTabelElements(@NotNull final RoundEnvironment roundEnv) throws ProcessingException {
    Set<? extends Element> tableElements = roundEnv.getElementsAnnotatedWith(Table.class);
    mTableTypeValidator.validate(tableElements);
    return tableElements;
  }

  private Set<? extends Element> findAndValidateColumnElements(@NotNull final RoundEnvironment roundEnv) throws ProcessingException {
    Set<? extends Element> columnElements = roundEnv.getElementsAnnotatedWith(Column.class);
    mColumnTypeValidator.validate(columnElements);
    return columnElements;
  }

  private Set<? extends Element> findAndValidateRepositoryElements(@NotNull final RoundEnvironment roundEnv) throws ProcessingException {
    Set<? extends Element> repositoryElements = roundEnv.getElementsAnnotatedWith(Repository.class);
    mRepositoryTypeValidator.validate(repositoryElements);
    return repositoryElements;
  }

  private Set<? extends TableClass> findAndValidateTableClasses(@NotNull final Set<? extends Element> tableElements) throws ProcessingException {
    Set<TableClass> tableClasses = new TableClassFactory().createTableClasses(tableElements);
    mTableClassValidator.validate(tableClasses);
    return tableClasses;
  }

  private Set<? extends RepositoryClass> findAndValidateRepositoryClasses(@NotNull final Set<? extends Element> repositoryElements) {
    return new RepositoryClassFactory().createRepositoryClasses(repositoryElements);
  }

  private void writeRepositoryClasses(@NotNull final Set<? extends RepositoryClass> repositoryClasses, @NotNull final Set<? extends TableClass> tableClasses)
      throws IOException, ProcessingException {
    for (RepositoryClass repositoryClass : repositoryClasses) {
      for (TableClass tableClass : tableClasses) {
        if (repositoryClass.getTableClassName().equals(tableClass.getClassName())
            && repositoryClass.getTableClassPackageName().equals(tableClass.getPackageName())) {
          writeRepositoryClass(repositoryClass, tableClass);
        }
      }
    }
  }

  private void writeRepositoryClass(@NotNull final RepositoryClass repositoryClass, @NotNull final TableClass tableClass) throws IOException, ProcessingException {
    TypeSpec repositoryTypeSpec = new RepositoryTypeSpecCreator(repositoryClass, tableClass).create();
    mTypeSpecWriter.writeToFile(repositoryClass.getPackageName(), repositoryTypeSpec);
  }
}