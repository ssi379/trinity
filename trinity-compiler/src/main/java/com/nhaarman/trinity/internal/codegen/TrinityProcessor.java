/*
 * Copyright (C) 2014 Michael Pardo
 * Copyright (C) 2014 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
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
import com.nhaarman.trinity.internal.codegen.table.TableClass;
import com.nhaarman.trinity.internal.codegen.table.TableInfoFactory;
import com.nhaarman.trinity.internal.codegen.table.column.validator.ColumnTypeValidator;
import com.nhaarman.trinity.internal.codegen.table.repository.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.table.repository.RepositoryInfoFactory;
import com.nhaarman.trinity.internal.codegen.table.repository.RepositoryWriter;
import com.nhaarman.trinity.internal.codegen.table.repository.validator.RepositoryTypeValidator;
import com.nhaarman.trinity.internal.codegen.table.validator.TableClassValidator;
import com.nhaarman.trinity.internal.codegen.table.validator.TableTypeValidator;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes("com.nhaarman.trinity.annotations.*")
public class TrinityProcessor extends AbstractProcessor {

  private TableTypeValidator mTableTypeValidator;

  private ColumnTypeValidator mColumnTypeValidator;

  private TableClassValidator mTableClassValidator;

  private RepositoryWriter mRepositoryWriter;

  private RepositoryTypeValidator mRepositoryTypeValidator;

  private Messager mMessager;

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  public synchronized void init(final ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    mMessager = processingEnv.getMessager();

    mTableTypeValidator = new TableTypeValidator(mMessager);
    mColumnTypeValidator = new ColumnTypeValidator(mMessager);
    mRepositoryTypeValidator = new RepositoryTypeValidator(mMessager);

    mTableClassValidator = new TableClassValidator(mMessager);

    mRepositoryWriter = new RepositoryWriter(processingEnv.getFiler());
  }

  @Override
  public synchronized boolean process(final Set<? extends TypeElement> annotations,
      final RoundEnvironment roundEnv) {

    /* Validate individual elements */
    Set<? extends Element> tableElements = roundEnv.getElementsAnnotatedWith(Table.class);
    if (!mTableTypeValidator.validate(tableElements)) {
      return true;
    }

    Set<? extends Element> columnElements = roundEnv.getElementsAnnotatedWith(Column.class);
    if (!mColumnTypeValidator.validate(columnElements)) {
      return true;
    }

    Set<? extends Element> repositoryElements = roundEnv.getElementsAnnotatedWith(Repository.class);
    if (!mRepositoryTypeValidator.validate(repositoryElements)) {
      return true;
    }

    /* Gather information about repositories, tables and columns */
    Collection<TableClass> tableClasses = new TableInfoFactory().createTableInfos(tableElements);
    mTableClassValidator.validate(tableClasses);

    Collection<RepositoryClass> repositoryClasses =
        new RepositoryInfoFactory().createRepositoryInfo(repositoryElements, tableClasses,
            roundEnv);

    for (RepositoryClass repositoryClass : repositoryClasses) {
      try {
        mRepositoryWriter.writeRepositoryClass(repositoryClass);
      } catch (IOException e) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, e.getLocalizedMessage());
        return true;
      }
    }

    return true;
  }
}